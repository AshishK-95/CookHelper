package ca.uottawa.cookhelper;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;


public class MainMenu extends AppCompatActivity {

    public static int actionBarHeight;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private CustomAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar myToolbar;
    private String[] textArray = { "Browse", "Cuisine", "Course", "Add Recipe", "Help"};

    //Declare all existing fragments that will be present in the MainMenu activity
    private AddRecipeFragment addRecipeFragment;
    public static BrowseFragment browseFragment;
    private CuisineFragment cuisineFragment;
    private CourseFragment courseFragment;
    private DraftsFragment draftsFragment;
    private HelpFragment helpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Create custom app bar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Customize the title of the app bar
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_textview, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        HomePage.font.setFontOf(title, 0);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        //Setup navigation drawer
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        addDrawerItems();
        setupDrawer();

        //Used to scale the drawer items to perfectly fit screen dimensions
        actionBarHeight = getActionBarHeight();

        //Prevent screen from dimming when navigation drawer is active
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);


        // Initialize all fragments of the MainMenu activity
        browseFragment = new BrowseFragment();
        cuisineFragment = new CuisineFragment();
        courseFragment = new CourseFragment();
        draftsFragment = new DraftsFragment();
        helpFragment = new HelpFragment();


        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, browseFragment).commit();

    }

    private void addDrawerItems() {
        mAdapter = new CustomAdapter(this, textArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
        myToolbar.setNavigationIcon(R.drawable.navigationbaricon1);
        myToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.overflowicon1));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_search:
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, searchFragment);
                transaction.commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onDrawerItemClick(View view) {
        View temp;
        TextView text;

        //Make the background of every drawer item to black
        for (int i = 0; i < mDrawerList.getCount();i++) {
            temp = mDrawerList.getChildAt(i);
            text = (TextView) temp.findViewById(R.id.textView1);
            text.setBackgroundResource(R.color.black);
        }

        TextView drawerItem = (TextView) view; //Obtain the TextView clicked
        drawerItem.setBackgroundResource(R.color.orange); //Set background of the clicked TextView to orange
        mDrawerLayout.closeDrawers();
        String drawerItemText = (String) drawerItem.getText();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (drawerItemText) {
            case "Browse":
                transaction.replace(R.id.fragment_container, browseFragment);
                transaction.commit();
                break;
            case "Cuisine":
                transaction.replace(R.id.fragment_container, cuisineFragment);
                transaction.commit();
                break;
            case "Course":
                transaction.replace(R.id.fragment_container, courseFragment);
                transaction.commit();
                break;
            case "Add Recipe":
                addRecipeFragment = new AddRecipeFragment();
                transaction.replace(R.id.fragment_container, addRecipeFragment);
                transaction.commit();
                break;
            case "Drafts":
                transaction.replace(R.id.fragment_container, draftsFragment);
                transaction.commit();
                break;
            case "Help":
                transaction.replace(R.id.fragment_container, helpFragment);
                transaction.commit();
                break;
        }
    }

    public int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        return getResources().getDimensionPixelSize(tv.resourceId);

    }
}
