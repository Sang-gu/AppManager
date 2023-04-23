package com.test.appmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.ViewHolder>{  // recyclerview 와 viewholder 클래스

    Context context;
    ArrayList<Item> recyclelist;

    public CustomRecycler(Context context, ArrayList<Item> recyclelist) {  // context와 앱 정보 리스트를 받아오는 생성자
        super();
        this.context = context;
        this.recyclelist = recyclelist;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageDrawable(recyclelist.get(position).image);
        holder.name.setText(recyclelist.get(position).name);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recyclelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{  // 뷰홀더
        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.appicon);
            name = itemView.findViewById(R.id.appname);

            image.setOnClickListener(new View.OnClickListener() {  // 앱 아이콘 클릭 이벤트 - intent와 context를 받아와 패키지명으로 앱 실행
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();  // 클릭된 개체 순번 가져오기
                    Intent intent = recyclelist.get(position).intent;
                    context.startActivity(intent);

                }
            });
        }


    }

}
