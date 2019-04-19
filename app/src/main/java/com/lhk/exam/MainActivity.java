package com.lhk.exam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.lhk.exam.sqlite.Constant;
import com.lhk.exam.sqlite.DBHelper;
import com.lhk.exam.sqlite.DBManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private DrawerLayout mDrawer;
    private NavigationView navigationView;

    private ListView listView;
    private SQLiteDatabase db;
    private DBHelper mHelper;

    private void initToolBar(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initFloatBtn(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initDrawerLayout(){
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initFloatBtn();
        initDrawerLayout();

        listView = findViewById(R.id.listView);
        mHelper = DBManager.getInstance(this);
        /**
         * openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags)
         * path 表示当前打开数据库的存放路径
         * factory 游标工厂
         * flags 表示打开数据库的操作模式
         */
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"info.db";
//        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);

        db = mHelper.getReadableDatabase();

        try {
            String sql = "insert into "+ Constant.TableUser.TABLE_NAME+" values(1,'张三',20)";
            DBManager.execSQL(db,sql);

            String sql2 = "insert into "+ Constant.TableUser.TABLE_NAME+" values(2,'李四',30)";
            DBManager.execSQL(db,sql2);
        } catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
//            db.close();
        }

        Cursor cursor = db.rawQuery("select * from "+ Constant.TableUser.TABLE_NAME,null);


        /**
         * SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
         * context 上下文对象
         * layout 表示适配器控件中每一项item的布局id
         * c 表示Cursor数据源
         * from 表示Cursor中数据表字段的数组
         * to 表示字段对应值的控件资源id
         * flags 设置适配器的标记
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item,cursor
                ,new String[]{Constant.TableUser._ID,Constant.TableUser.NAME,Constant.TableUser.AGE}
                ,new int[]{R.id.tv_id,R.id.tv_name,R.id.tv_age}
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

        db.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
