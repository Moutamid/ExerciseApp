package com.moutamid.exercises.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.exercises.Adapter.CalendarRecyclerViewAdapter;
import com.moutamid.exercises.Adapter.ExerciseAdapter;
import com.moutamid.exercises.Adapter.ExerciseBuddiesAdapter;
import com.moutamid.exercises.Authentication.SignupActivity;
import com.moutamid.exercises.DataBase.Exercise;
import com.moutamid.exercises.Model.DateModelClass;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.ExerciseManager;
import com.moutamid.exercises.databinding.FragmentHistoryBinding;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HistoryFragment extends Fragment {

    public static FragmentHistoryBinding binding;
    private CalendarRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static LocalDate startDate, endDate, todayDate;
    boolean changeDetected = false;
    boolean scrollPager = true;
    boolean clicked = false;

    public static LocalDate selected;
    private ArrayList<DateModelClass> dateArrayList;
    private List<LocalDate> dates;
    public static String targetDate;
    private static int lastClickedItemPosition = -1;
    public static ExerciseAdapter adapter;
    public static List<Exercise> exerciseList;
    public static ExerciseBuddiesAdapter exerciseBuddiesAdapter;
    public static List<Exercise> exerciseListBuddies;
    public static ExerciseManager exerciseManager;
    public static Dialog lodingbar;
    private static SparseArray<CalendarRecyclerViewAdapter.ViewHolder> viewHolders = new SparseArray<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        dateArrayList = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        Calendar(getContext(), getActivity());
//        dbHelper = new ExerciseDbHelper(getContext());
//        targetDate = "2024-5-10";
//        exercises = dbHelper.getExercisesByDate(targetDate);
//        binding.myRecyclerViewTrack.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new ExerciseAdapter(exercises);
//        binding.myRecyclerViewTrack.setAdapter(adapter);
//                progressBar.setVisibility(View.VISIBLE);
        lodingbar = new Dialog(getContext());
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        binding.myRecyclerViewTrack.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseList = new ArrayList<>();
        adapter = new ExerciseAdapter(exerciseList);
        binding.myRecyclerViewTrack.setAdapter(adapter);
        exerciseManager = new ExerciseManager();
        targetDate = getCurrentDate();
        fetchExercisesByDate(targetDate);
        binding.myRecyclerViewBuddies.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseListBuddies = new ArrayList<>();
        exerciseBuddiesAdapter = new ExerciseBuddiesAdapter(exerciseListBuddies);
        binding.myRecyclerViewBuddies.setAdapter(exerciseBuddiesAdapter);
        fetchBuddiesExercisesByDate(targetDate);
        return binding.getRoot();

    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH);
        return sdf.format(new Date());
    }

    public static LocalDate getSelected() {
        return selected;
    }

    public void Calendar(Context context, Activity activity) {
        startDate = new LocalDate(2022, 1, 1);
        endDate = new LocalDate(2050, 12, 31);
        todayDate = new LocalDate();
        initControl(context, activity);
    }

    public static void fetchExercisesByDate(String date) {
        exerciseManager.getExercisesByDate(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exerciseList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Exercise exercise = snapshot.getValue(Exercise.class);
                    exerciseList.add(exercise);
                }
                lodingbar.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                lodingbar.dismiss();

            }
        });
    }

    public static void fetchBuddiesExercisesByDate(String date) {
        exerciseListBuddies.clear();

        ArrayList<String> userIdList = Stash.getArrayList("userIdList", String.class);
        for (String id : userIdList) {

            DatabaseReference exercisesRef = FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(id).child("Exercises");

            exercisesRef.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exercise exercise = snapshot.getValue(Exercise.class);
//                        if (userIdList.contains(snapshot.getKey())) {
                            exerciseListBuddies.add(exercise);
//                        }
                    }
                    lodingbar.dismiss();
                    exerciseBuddiesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors.
                    lodingbar.dismiss();

                }
            });
        }
    }

    @SuppressLint("ResourceAsColor")
    private void initControl(Context context, Activity activity) {

        binding.monthYear.setBackgroundColor(com.moutamid.exercises.R.color.app_color);
        setUpRecyclerView(context);
        setUpListeners(context);
    }

    private void setUpListeners(final Context context) {
        Log.d("dateeee", todayDate.getMonthOfYear() + "  " + todayDate.getYear());
        setMonthYearText(getMonthName(todayDate.getMonthOfYear()), "" + todayDate.getYear());
        LocalDate t = new LocalDate();
        if (selected != null && !selected.isEqual(t)) {
            selected = t;
        } else if (selected == null) {
            selected = t;
        }
        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPositionWithOffset(Days.daysBetween(startDate, todayDate).getDays() - 1, 0);

    }

    private void setUpRecyclerView(final Context context) {
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.myRecyclerView.setLayoutManager(mLayoutManager);
        dates = new ArrayList<>();
        Log.d("start", startDate + "  " + endDate);
        int days = Days.daysBetween(startDate, endDate).getDays();
        for (int i = 0; i < days; i++) {
            LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d);
        }
        if (dateArrayList != null) {
            mAdapter = new CalendarRecyclerViewAdapter(dates, dateArrayList);
            binding.myRecyclerView.setAdapter(mAdapter);
        }
        mLayoutManager.scrollToPositionWithOffset(Days.daysBetween(startDate, todayDate).getDays() - 1, 0);
        binding.myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrollPager) {
                    int position = mLayoutManager.findFirstVisibleItemPosition() + 1;
                    LocalDate d = dates.get(position);
                    int year = d.getYear();
                    int month = d.getMonthOfYear();
                    setMonthYearText(getMonthName(month), "" + year);
                    changeDetected = true;
                    changeDetected = false;
                } else {
                    scrollPager = true;
                }
            }
        });

    }

    private String getMonthName(int month) {

        switch (month) {

            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return null;
    }

    private void setMonthYearText(String month, String year) {
        binding.monthYear.setText(month + ", " + year);
    }

    public static int getLastClickedItemPosition() {
        return lastClickedItemPosition;
    }

    public static void setLastClickedItemPosition(int position) {
        lastClickedItemPosition = position;
    }

    public static CalendarRecyclerViewAdapter.ViewHolder getViewHolder(int position) {
        return viewHolders.get(position);
    }

    public static void putViewHolder(int position, CalendarRecyclerViewAdapter.ViewHolder viewHolder) {
        viewHolders.put(position, viewHolder);
    }

}