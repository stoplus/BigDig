package com.example.den.bigdig;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private MenuItem itemSortByDate;
    private MenuItem itemSortByStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {//слушатель переключения вкладок таб
            @Override  //на какой таб переходим
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        if (itemSortByDate != null) {
                            itemSortByDate.setVisible(true);
                            itemSortByStatus.setVisible(true);
                        }
                        break;
                    default:
                        if (itemSortByDate != null) {
                            itemSortByDate.setVisible(false);
                            itemSortByStatus.setVisible(false);
                        }
                        break;
                }//switch
            }//onTabSelected

            @Override  //с какого таб переходим
            public void onTabUnselected(TabLayout.Tab tab) {
            }//onTabUnselected

            @Override  //повторное нажатие на таб
            public void onTabReselected(TabLayout.Tab tab) {
            }//onTabReselected
        });
        setupViewPagerAdapter();
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager, true);//прикрепляем ViewPager к TabLayout
    }

    //-------------------------------------------------------------------------------------
    private void setupViewPagerAdapter() {
        //передаем данные в страници (фрагменты) ViewPage
        //создаем фрагменты
        FragmentTest fragmentTest = new FragmentTest();
        FragmentListLinks fragmentListLinks = new FragmentListLinks();

        //создаем адаптер для ViewPager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //добавляем страници и названия страниц в адаптер
        adapter.addFragment(fragmentTest, "Тест");
        adapter.addFragment(fragmentListLinks, "История");
    }//setupViewPagerAdapter

    //создаем и заполняем данными ViewPager
    public void setupViewPager() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);//присваиваем ViewPager адаптер
        viewPager.setOffscreenPageLimit(2);
    }//setupViewPager

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//закачиваем меню из xml
        itemSortByDate = menu.findItem(R.id.mSortByDate); //определяем Item добавить
        itemSortByStatus = menu.findItem(R.id.mSortByStatus);//определяем Item выход

        return super.onCreateOptionsMenu(menu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        switch (id) {
            case R.id.mSortByStatus:
                FragmentListLinks.adapter.sortByStatus();
                break;
            case R.id.mSortByDate:
                FragmentListLinks.adapter.sortByDate();
                break;
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected
}
