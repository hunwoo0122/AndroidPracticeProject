package com.example.termproject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 아래는 이미 작성된 메모 리스트를 보여주는 데에 필요한 멤버들
    Context context;
    RecyclerView memoList;
    MemoListAdapter memoListAdapter; // 리사이클러뷰의 어댑터
    LinearLayoutManager layoutManager; //리사이클러뷰의 레이아웃 결정

    // 아래는 메모 작성 시 필요한 멤버들
    Spinner categorySpinner;
    EditText memoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        setView();
    }

    // 레이아웃에서 뷰 찾고 버튼 리스너 설정
    private void setView() {
        categorySpinner = findViewById(R.id.category);
        memoEdit = findViewById(R.id.memo);

        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(this);

        memoList = findViewById(R.id.recyclerView);

        setRecyclerView();
        setMemoListItem();
    }

    // 리사이클러뷰 설정: 레이아웃 매니저, 어댑터 설정
    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        memoList.setLayoutManager(layoutManager);

        memoListAdapter = new MemoListAdapter(context, R.layout.row_memo_item, new ArrayList<MemoItem>());
        memoList.setAdapter(memoListAdapter);
    }

    // 리사이클러뷰에 아이템 지정, 설정할 더미 아이템은 getMemoList에서 생성
    private void setMemoListItem () {
        ArrayList<MemoItem> list = getMemoDummyList();
        memoListAdapter.addItemList(list);
    }

    private ArrayList<MemoItem> getMemoDummyList() {
        ArrayList<MemoItem> list = new ArrayList<>();

        // sample items
        MemoItem item1 = new MemoItem("일상", "6시 운동");
        MemoItem item2 = new MemoItem("학교", "12시 모바일 프로그래밍 수업 끝나고 회의");

        list.add(item1);
        list.add(item2);

        return list;
    }

    // 등록 버튼 클릭하면, 메모 등록 메서드 실행
    @Override
    public void onClick(View v) {
        registerMemo();
    }


    // 메모 등록 메서드: 아이템 추가, 뷰 초기화, 키패드 숨기기
    private void registerMemo () {
        String category = (String) categorySpinner.getSelectedItem();
        String memo = memoEdit.getText().toString();

        if(TextUtils.isEmpty(memo)) {
            Toast.makeText(context, R.string.msg_memo_input, Toast.LENGTH_SHORT) .show();
            return;
        }

        addMemoItem(category, memo);

        categorySpinner.setSelection(0);
        memoEdit.setText("");

        hideKeyboard();
    }

    // 키패드 숨기기 메서드
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 아이템을 어댑터에 추가하는 매서드
    private void addMemoItem(String category, String memo) {
        MemoItem item = new MemoItem(category, memo);

        memoListAdapter.addItem(item);
    }
}


