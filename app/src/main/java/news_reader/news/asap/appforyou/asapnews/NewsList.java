package news_reader.news.asap.appforyou.asapnews;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import utils.FetchNews;
import utils.NewsArticle;
import utils.Topic;
import utils.TopicListDataBase;
import utils.ZoomOutPageTransformer;

public class NewsList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GestureDetector.OnGestureListener {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int NUM_PAGES = 5;
    ArrayList<NewsArticle> newsArrayList;
    // DiscreteSlider discreteSlider;
    ArrayList<Topic> topicArrayList = new ArrayList<>();
    TextView topicText;

    RelativeLayout relativeLayout;
    GestureDetector gestureDetector;
    CardView categoryChooser;
    FABRevealMenu fabRevealMenu;
    FABToolbarLayout fabToolbarLayout;

    ImageView splashScreen;
     FloatingActionButton fab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        splashScreen= (ImageView)findViewById(R.id.splashScreen_View);
        splashScreen.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fabToolbarLayout.show();

               *//* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/


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


        /*discreteSlider = (DiscreteSlider) findViewById(R.id.newsList_discreteSlider);
        discreteSlider.setTickMarkCount(3);
        discreteSlider.setOnDiscreteSliderChangeListener(new DiscreteSlider.OnDiscreteSliderChangeListener() {
            @Override
            public void onPositionChanged(int position) {

                mPager.setCurrentItem(topicArrayList.get(position).getTopicStartIndex());

                setTopicText(topicArrayList.get(position).getTopicName());

            }
        });
*/
        gestureDetector = new GestureDetector(NewsList.this, NewsList.this);
        relativeLayout = (RelativeLayout) findViewById(R.id.content_news_list);
        mPager = (ViewPager) findViewById(R.id.viewPager);

        categoryChooser = (CardView) findViewById(R.id.newsList_categoryChooser_cardView);


        mPager.setOnTouchListener(new View.OnTouchListener() {

            float x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = event.getY();
                        // Toast.makeText(NewsList.this, "touch down", Toast.LENGTH_SHORT).show();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        if (y - event.getY() > 200) {
                            //  Log.i("Some", "onTouch: swipping");
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //Toast.makeText(NewsList.this, "touch up", Toast.LENGTH_SHORT).show();
                        if (y - event.getY() > 250) {

                            openLink();


                        } else {

                        }
                        fabToolbarLayout.hide();
                        break;
                }

                return false;
            }
        });

        fabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
         fab2 = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabToolbarLayout.show();

            }
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //Toast.makeText(NewsList.this, "page is transition", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageSelected(int position) {

                // Toast.makeText(NewsList.this, "page selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(NewsList.this, "state = "+state, Toast.LENGTH_SHORT).show();

                if (state == 1) {
                    fab2.hide();
                } else {
                    fab2.show();
                }

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
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

       switch(id){
           case R.id.nav_india:
               openCategory(0,false);
               break;
           case R.id.nav_international:
               openCategory(1,false);

               break;
           case R.id.nav_technology:
               openCategory(2,false);

               break;
           case R.id.nav_science:
               openCategory(3,false);

               break;
           case R.id.nav_share:
               onShareClick();
               break;
           case R.id.nav_rate_us:
               onRateUsClick();
               break;
           case R.id.nav_visit_us:
               onVisitUsClick();
               break;
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onRateUsClick() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=news_reader.news.asap.appforyou.asapnews")));
        }catch(Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void onVisitUsClick() {
        new FinestWebView.Builder(this)
                .toolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .show("https://appforyou.wixsite.com/android");

    }

    private void onShareClick() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, " Download ASAP News - Light, Fast and Reliable \n Getnews from trusted source ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=news_reader.news.asap.appforyou.asapnews");
        sharingIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ASAP News");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, "ASAP News1");



        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void openCategory(int i, boolean isFabLayoutAction) {
        if (mPager != null) {
            mPager.setCurrentItem(topicArrayList.get(i).getTopicStartIndex());

            setTopicText(topicArrayList.get(i).getTopicName());
            if(isFabLayoutAction) {
                fabToolbarLayout.hide();
            }
        }
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
        if (mPager.getCurrentItem() == j) {


            mPager.setAdapter(mPagerAdapter);

            mPager.setCurrentItem(j);

        }

    }

    public void fetchEngadgetListner(ArrayList<NewsArticle> newsArticles) {

        for (int i = 0; i < newsArticles.size(); i++) {
            this.newsArrayList.add(newsArticles.get(i));
        }
        mPagerAdapter.notifyDataSetChanged();

    }

    public void fetchNewsFromSourceListner(ArrayList<NewsArticle> newsArticles, int topicPriority) {

        if (topicPriority == 0) {
            this.newsArrayList = newsArticles;
            //NUM_PAGES = this.newsArrayList.size();

            initializeViewPager();
            closeSplashScreen();


        } else {
            topicArrayList.get(topicPriority).setTopicStartIndex(this.newsArrayList.size());


            for (int i = 0; i < newsArticles.size(); i++) {
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

                if (position == 0 || position == topicArrayList.get(0).getTopicStartIndex() - 1) {
                    setTopicText(topicArrayList.get(0).getTopicName());
                } else if (position == topicArrayList.get(1).getTopicStartIndex() || position ==
                        topicArrayList.get(2).getTopicStartIndex() - 1) {
                    setTopicText(topicArrayList.get(1).getTopicName());
                } else if (position == topicArrayList.get(2).getTopicStartIndex() || position ==
                        topicArrayList.get(3).getTopicStartIndex() - 1) {
                    setTopicText(topicArrayList.get(2).getTopicName());
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if (motionEvent1.getY() - motionEvent2.getY() > 50) {

            Toast.makeText(NewsList.this, " Swipe Up ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > 50) {

            Toast.makeText(NewsList.this, " Swipe Down ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (motionEvent1.getX() - motionEvent2.getX() > 50) {

            Toast.makeText(NewsList.this, " Swipe Left ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (motionEvent2.getX() - motionEvent1.getX() > 50) {

            Toast.makeText(NewsList.this, " Swipe Right ", Toast.LENGTH_LONG).show();

            return true;
        } else {

            return true;
        }
    }

    @Override
    public void onLongPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
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


    public void categoryOneSelected(View view) {
        switch (view.getId()) {
            case R.id.one:
                openCategory(0,true);

                break;
            case R.id.one2:
                openCategory(1,true);


                break;
            case R.id.one3:
                openCategory(2,true);
                break;
            case R.id.one4:

                openCategory(3,true);
                break;


        }
    }

    public void openLink() {

        String url = newsArrayList.get(mPager.getCurrentItem()).getNewsURL();

        new FinestWebView.Builder(this)
                .toolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .show(url);


    }


    public void closeSplashScreen(){


        int x= (int)fab2 .getScaleX();
        int y= (int)fab2 .getY();
        Toast.makeText(this, " X and y is "+x+""+y, Toast.LENGTH_SHORT).show();

        float radius=Math.max(splashScreen.getWidth(),splashScreen.getHeight()) ;

        Animator reveal= ViewAnimationUtils.createCircularReveal(splashScreen,x,y,radius,fab2.getHeight());
        reveal.setInterpolator(new AccelerateDecelerateInterpolator());
        reveal.setDuration(600);

        reveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                splashScreen.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        reveal.start();


    }

}
