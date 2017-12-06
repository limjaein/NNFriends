package com.android.project.nnfriends_;

import android.os.Bundle;
import android.view.Display;
import android.widget.ExpandableListView;

import com.android.project.nnfriends_.Classes.Call;
import com.android.project.nnfriends_.Classes.CallGroup;

import java.util.ArrayList;

public class FriendActivity extends MyActivity {
    private ExpandableListView exlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        init();
    }

    private void init() {
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        ArrayList<CallGroup> DataList = new ArrayList<>();
        exlistView = (ExpandableListView)findViewById(R.id.callList);

        CallGroup cg = new CallGroup("어린이대공원");

        ArrayList<Call> people = new ArrayList<>();
        people.add(new Call("JaeIn","01085286812"));
        people.add(new Call("EunMi","01051982889"));

        cg.setChild(people);

        CallGroup cg2 = new CallGroup("새천년관");

        ArrayList<Call> people2 = new ArrayList<>();
        people2.add(new Call("SeoungRi","01085286812"));
        people2.add(new Call("YoungSeo","01051982889"));

        cg2.setChild(people2);

        DataList.add(cg);
        DataList.add(cg2);

        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.call_grouplist,R.layout.call_childlist, DataList);
        //exlistView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        exlistView.setAdapter(adapter);
        //강제펼침
//        for(int i=0; i<adapter.getGroupCount();i++){
//            exlistView.expandGroup(i);
//        }
    }


}
