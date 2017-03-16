package news_reader.news.asap.appforyou.asapnews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;

import java.util.ArrayList;

import utils.FetchNews;
import utils.NewsArticle;
import utils.Topic;
import utils.TopicListDataBase;
import utils.ZoomOutPageTransformer;

public class NewsList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int NUM_PAGES = 5;
    ArrayList<NewsArticle> newsArrayList;
    DiscreteSlider discreteSlider;
    ArrayList<Topic> topicArrayList = new ArrayList<>();
    TextView topicText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initializeTopicList();
        new FetchNews(this, topicArrayList).startFetching();
        Log.d("app", "onCreate: after request");


        topicText = (TextView) findViewById(R.id.newsList_topic_textView);
        setTopicText(topicArrayList.get(0).getTopicName());


        discreteSlider = (DiscreteSlider) findViewById(R.id.newsList_discreteSlider);
        discreteSlider.setTickMarkCount(3);
        discreteSlider.setOnDiscreteSliderChangeListener(new DiscreteSlider.OnDiscreteSliderChangeListener() {
            @Override
            public void onPositionChanged(int position) {

                mPager.setCurrentItem(topicArrayList.get(position).getTopicStartIndex());

                setTopicText(topicArrayList.get(position).getTopicName());

            }
        });




    }

    private void setTopicText(String topicName) {
        topicText.setText(topicName);
    }

    private void initializeTopicList() {

        topicArrayList = new TopicListDataBase(this).getTopicList();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ( drawer.isDrawerOpen(GravityCompat.START) ) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings ) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.nav_camera ) {
            // Handle the camera action
        } else if ( id == R.id.nav_gallery ) {

        } else if ( id == R.id.nav_slideshow ) {

        } else if ( id == R.id.nav_manage ) {

        } else if ( id == R.id.nav_share ) {

        } else if ( id == R.id.nav_send ) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fetchToiNewsListner(ArrayList<NewsArticle> newsArraylist) {
        this.newsArrayList = newsArraylist;
        NUM_PAGES = this.newsArrayList.size();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());


    }

    public void onFetchImageCallBack(int j) {

   /*     Configuration configuration = this.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        int screenHeightDp = configuration.screenHeightDp;

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int p = (int) (screenWidthDp * scale + 0.5f);
        int q = (int) ((screenHeightDp / 3) * scale + 0.5f);

        newsArrayList.get(j).setNewsImage(Bitmap.createScaledBitmap(newsArrayList.get(j)
                .getNewsImage(), p, q, true));
*/
        if ( mPager.getCurrentItem() == j ) {


            mPager.setAdapter(mPagerAdapter);

            mPager.setCurrentItem(j);

        }

    }

    public void fetchEngadgetListner(ArrayList<NewsArticle> newsArticles) {

        for ( int i = 0; i < newsArticles.size(); i++ ) {
            this.newsArrayList.add(newsArticles.get(i));
        }
        mPagerAdapter.notifyDataSetChanged();

    }

    public void fetchNewsFromSourceListner(ArrayList<NewsArticle> newsArticles, int topicPriority) {

        if ( topicPriority == 0 ) {
            this.newsArrayList = newsArticles;
            //NUM_PAGES = this.newsArrayList.size();

            initializeViewPager();



        } else {
            topicArrayList.get(topicPriority).setTopicStartIndex(this.newsArrayList.size());


            for ( int i = 0; i < newsArticles.size(); i++ ) {
                this.newsArrayList.add(newsArticles.get(i));
            }
            mPagerAdapter.notifyDataSetChanged();
        }

    }

    private void initializeViewPager() {

// Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if ( position == 0 || position == topicArrayList.get(0).getTopicStartIndex() - 1 ) {
                    setTopicText( topicArrayList.get(0).getTopicName());
                }else if ( position == topicArrayList.get(1).getTopicStartIndex() || position ==
                        topicArrayList.get(2).getTopicStartIndex()-1  ){
                    setTopicText( topicArrayList.get(1).getTopicName());
                }else if ( position == topicArrayList.get(2).getTopicStartIndex() || position ==
                        topicArrayList.get(3).getTopicStartIndex()-1  ){
                    setTopicText( topicArrayList.get(2).getTopicName());
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsArticleViewFragment.newInstance("New", newsArrayList.get(position));
        }

        @Override
        public int getCount() {
            return newsArrayList.size();
        }
    }
}
