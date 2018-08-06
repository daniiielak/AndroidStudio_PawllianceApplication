package pawlliance.com.pawlliance.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pawlliance.com.pawlliance.R;

public class WalkingActivityListAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<String> walkingActivitiesWalkingDatesList;
    ArrayList<String> walkingActivitiesDogsList;
    ArrayList<Double> walkingActivitiesTotalWalkingTimeList;
    ArrayList<Double> walkingActivitiesTotalWalkingDistanceList;
    ArrayList<String> walkingActivitiesDescriptionList;

    public WalkingActivityListAdapter(@NonNull Context context, ArrayList<String> walkingActivitiesWalkingDatesList, ArrayList<String> walkingActivitiesDogsList, ArrayList<Double> walkingActivitiesTotalWalkingTimeList, ArrayList<Double> walkingActivitiesTotalWalkingDistanceList, ArrayList<String> walkingActivitiesDescriptionList) {
        this.walkingActivitiesWalkingDatesList = walkingActivitiesWalkingDatesList;
        this.walkingActivitiesDogsList = walkingActivitiesDogsList;
        this.walkingActivitiesTotalWalkingTimeList = walkingActivitiesTotalWalkingTimeList;
        this.walkingActivitiesTotalWalkingDistanceList = walkingActivitiesTotalWalkingDistanceList;
        this.walkingActivitiesDescriptionList = walkingActivitiesDescriptionList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return walkingActivitiesWalkingDatesList.size();
    }


    @Override
    public Object getItem(int i){
        return walkingActivitiesWalkingDatesList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }


    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.layout_walkingactivities, null);

        TextView layoutWalkingActivityDate = (TextView) v.findViewById(R.id.LayoutWalkingActivityDate);
        TextView layoutWalkingActivityDog = (TextView) v.findViewById(R.id.LayoutWalkingActivityDog);
        TextView layoutWalkingActivitiesTotalWalkingTime = (TextView) v.findViewById(R.id.LayoutWalkingActivitiesTotalWalkingTime);
        TextView layoutWalkingActivitiesTotalDistance = (TextView) v.findViewById(R.id.LayoutWalkingActivitiesTotalDistance);
        TextView layoutWalkingActivitiesWalkingDescription = (TextView) v.findViewById(R.id.LayoutWalkingActivitiesWalkingDescription);


        // get the user information that we need for item layout
        String walkingDate = walkingActivitiesWalkingDatesList.get(i);
        String walkingDog = walkingActivitiesDogsList.get(i);
        Double walkingTime = walkingActivitiesTotalWalkingTimeList.get(i);
        Double walkingDistance = walkingActivitiesTotalWalkingDistanceList.get(i);
        String walkingDescription = walkingActivitiesDescriptionList.get(i);

        layoutWalkingActivityDate.setText(walkingDate);
        layoutWalkingActivityDog.setText(walkingDog);
        layoutWalkingActivitiesTotalWalkingTime.setText(walkingTime.toString());
        layoutWalkingActivitiesTotalDistance.setText(walkingDistance.toString());
        layoutWalkingActivitiesWalkingDescription.setText(walkingDescription);
        return v;
    }


    // DNC - class closing tag
}
