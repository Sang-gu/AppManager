/*
* 2023.04.23
* 제작자 : 이상구
* 프로젝트 : 애플리케이션 관리 앱
*/



package com.test.appmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.test.appmanager.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // 멤버 변수 선언
    private ActivityMainBinding binding;  // 뷰 바인딩 변수

    RecyclerView recyclerView;  // recyclerview
    CustomRecycler adapter;  // recyclerview 어댑터
    GridLayoutManager gridLayout;

    ArrayList<Item> applist = new ArrayList<Item>();  // adapter에 넣어줄 Item들을 담을 리스트
    ArrayList<ActivityInfo> appInfo;  // 가져온 앱 정보를 담을 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());  // activity_main.xml과 뷰 바인딩
        View view = binding.getRoot();
        setContentView(view);

        appInfo = getAppInfo(this);  // category.LAUNCHER인 앱의 정보를 받아오는 getAppInfo 메서드 호출

        for (int i = 0; i < appInfo.size(); i++){  // 받아온 ActivityInfo로 앱 이름과 아이콘, 패키지명을 추출
            String name = appInfo.get(i).loadLabel(getPackageManager()).toString();
            Drawable icon = appInfo.get(i).loadIcon(getPackageManager());
            String pkgname = appInfo.get(i).packageName;
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(pkgname);  // 패키지명으로 앱 실행 intent 생성
            if (Objects.equals(pkgname, "com.test.appmanager")){  // 이 앱 자기 자신은 제외
                continue;
            }
            applist.add(new Item(name, icon, intent, this));  // Item 생성자 파라미터로 앱 이름, 아이콘, 앱 실행을 위한 intent와 context 입력
        }

        recyclerView = binding.recycler;  // 뷰바인딩된 recylerview
        adapter = new CustomRecycler(this, applist);

        gridLayout = new GridLayoutManager(this, 3);  // 한 줄에 세개씩 넣는 그리드 리사이클러뷰
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<ActivityInfo> getAppInfo(Context context){  // 설치된 앱 목록 가져오는 메서드

        ArrayList<ActivityInfo> pkgInfo = new ArrayList<>();  // ActivityInfo를 넣어 return 해줄 리스트
        PackageManager pkm = context.getPackageManager();  // 패키지매니저 생성

        Intent intent = new Intent(Intent.ACTION_MAIN);  // 시작화면 activity
        intent.addCategory(Intent.CATEGORY_LAUNCHER);  // 보내지는 intent의 카테고리 지정
        List<ResolveInfo> scApps = pkm.queryIntentActivities(intent, 0);  // intent로 실행될 컴포넌트 정보를 resolveInfo 형식으로 반환
        // resolveInfo 안에는 앱 이름 등의 정보들이 대부분 들어있음

        for (int i = 0; i < scApps.size(); i++){
            pkgInfo.add(scApps.get(i).activityInfo);
        }
        return pkgInfo;
    }


}

