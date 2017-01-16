package net.ginteam.carmen.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.company.CompanyDetailActivity;
import net.ginteam.carmen.view.fragment.MainFragment;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.city.CityListFragment;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

public class MainActivity extends NavigationViewActivity implements MainFragment.OnMainFragmentShowedListener, CategoryListFragment.OnCategorySelectedListener,
        CityListFragment.OnCitySelectedListener, CompanyListFragment.OnCompanySelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        Toast.makeText(this, R.string.test, Toast.LENGTH_SHORT).show();
        prepareFragment(MainFragment.newInstance(), false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilterActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String searchFilter = data.getStringExtra(FilterActivity.RESULT_FILTER_ARGUMENT);
            if (mCurrentFragment.getClass().equals(CompanyListFragment.class)) {
                ((CompanyListFragment) mCurrentFragment).setSearchFilter(searchFilter);
            }
            Log.d("FilterActivity", searchFilter);
        }
    }

    @Override
    public void onMainFragmentShowed() {
        setTitle(getString(R.string.main_item_text));
        setSubtitle("");
    }

    @Override
    public void onCategorySelected(CategoryModel category) {
        Toast.makeText(this, category.getName(), Toast.LENGTH_SHORT).show();
        prepareFragment(CompanyListFragment.newInstance(
                CompanyListFragment.COMPANY_LIST_TYPE.VERTICAL,
                CompanyListFragment.FETCH_COMPANY_TYPE.FOR_CATEGORY,
                category.getId()),
                true
        );
        setTitle(category.getName());
        setSubtitle("Кривой Рог");

        startFilterActivityForResult(category);
    }

    @Override
    public void onCitySelected(CityModel city) {
        Toast.makeText(this, city.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompanySelected(CompanyModel company) {
        Toast.makeText(this, company.getName(), Toast.LENGTH_SHORT).show();

        Bundle arguments = new Bundle();
        arguments.putInt(CompanyDetailActivity.COMPANY_ARGUMENT, company.getId());
        ActivityUtils.showActivity(CompanyDetailActivity.class, arguments, false);
    }

    private void startFilterActivityForResult(CategoryModel categoryModel) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra(FilterActivity.CATEGORY_ID_ARGUMENT, categoryModel.getId());
        startActivityForResult(intent, FilterActivity.REQUEST_CODE);
    }

}
