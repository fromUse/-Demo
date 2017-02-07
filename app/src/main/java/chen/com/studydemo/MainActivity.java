package chen.com.studydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private Button top;
    private Button bottom;
    private Set<ImageView> imgManager = new HashSet<>();
    public static String[] imgs = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        top = (Button) findViewById(R.id.top);
        bottom = (Button) findViewById(R.id.bottom);
        listView.setAdapter(new ListAdapter(listView));
        top.setOnClickListener(this);
        bottom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                listView.smoothScrollToPositionFromTop(0,6,500);
                break;
            case R.id.bottom:
                listView.smoothScrollToPositionFromTop(imgs.length,5,500);
                break;
        }
    }


    class ListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
        int a, b;
        private boolean isFirstLoad = true;

        public ListAdapter(ListView listview) {
            listview.setOnScrollListener(this);
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public String getItem(int position) {
            return imgs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(MainActivity.this, R.layout.list_item, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.imageView.setTag(imgs[position]);
            return convertView;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (b >= imgs.length) {
                    Log.i(TAG, "onScrollStateChanged: " + "到底");
                } else if (a == 0) {
                    Log.i(TAG, "onScrollStateChanged: " + "到顶");

                }
                for (; a < b; a++) {
                    Picasso.with(MainActivity.this).load(imgs[a]).into((ImageView) view.findViewWithTag(imgs[a]));
                    imgManager.add((ImageView) view.findViewWithTag(imgs[a]));
                }
            } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                for (ImageView imageView : imgManager) {
                    Picasso.with(MainActivity.this).cancelRequest(imageView);
                }
            }
            Log.i(TAG, "onScrollStateChanged: ");
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            a = firstVisibleItem;
            b = a + visibleItemCount;
            Log.i(TAG, "onScroll: " + firstVisibleItem + "  " + visibleItemCount + " " + totalItemCount);


            if (isFirstLoad){
                onScrollStateChanged(view,SCROLL_STATE_IDLE);
                isFirstLoad = false;
            }


        }
    }

    class Holder {
        ImageView imageView;
    }


}
