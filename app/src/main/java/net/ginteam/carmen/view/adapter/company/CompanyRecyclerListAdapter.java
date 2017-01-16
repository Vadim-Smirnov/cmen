package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

import java.util.List;
import java.util.Locale;

import static net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter.ITEM_VIEW_TYPE.COMPANY;
import static net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter.ITEM_VIEW_TYPE.LOADING;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyRecyclerListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    enum ITEM_VIEW_TYPE {
        COMPANY,
        LOADING
    }

    private Context mContext;
    private CompanyListFragment.COMPANY_LIST_TYPE mType;
    private List <CompanyModel> mCompanies;

    private boolean mIsLoadingIndicatorShow;

    private CompanyItemViewHolder.OnCompanyItemClickListener mCompanyItemClickListener;

    public CompanyRecyclerListAdapter(Context context, List <CompanyModel> companies, CompanyListFragment.COMPANY_LIST_TYPE type) {
        mContext = context;
        mType = type;
        mCompanies = companies;
        mIsLoadingIndicatorShow = false;
    }

    public void setOnCompanyItemClickListener(CompanyItemViewHolder.OnCompanyItemClickListener listener) {
        mCompanyItemClickListener = listener;
    }

    public void addCompanies(List <CompanyModel> companies) {
        int insertPosition = mCompanies.size();
        mCompanies.addAll(companies);
        notifyItemRangeInserted(insertPosition, companies.size());
    }

    public void showLoading() {
        mIsLoadingIndicatorShow = true;
        mCompanies.add(new CompanyModel());
        notifyItemInserted(mCompanies.size() - 1);
    }

    public void hideLoading() {
        mIsLoadingIndicatorShow = false;

        int position = mCompanies.size() - 1;
        CompanyModel item = mCompanies.get(position);

        if (item != null) {
            mCompanies.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMPANY.ordinal()) {
            View companyItemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(getListItemIdForType(mType), parent, false);
            return new CompanyItemViewHolder(companyItemView);
        } else {
            View loadingItemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_progress, parent, false);
            return new CompanyLoadingViewHolder(loadingItemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != COMPANY.ordinal()) {
            return;
        }

        CompanyModel currentCompany = mCompanies.get(position);
        CompanyItemViewHolder companyViewHolder = (CompanyItemViewHolder) holder;

        companyViewHolder.getTextViewName().setText(currentCompany.getName());
        companyViewHolder.getTextViewAddress().setText(currentCompany.getAddress());
        companyViewHolder.getRatingViewRating().setRating(currentCompany.getRating());
        companyViewHolder.getTextViewDistance().setText("300 м");
        companyViewHolder.getTextViewPrice().setText(
                String.format(Locale.getDefault(),
                        mContext.getString(R.string.company_price),
                        currentCompany.getPrice())
        );

        companyViewHolder.getImageButtonAddToFavorite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Like",Toast.LENGTH_SHORT).show();
            }
        });

        if (mCompanyItemClickListener == null) {
            Log.e("CompanyListAdapter", "mCompanyItemClickListener does not set!");
            return;
        }
        companyViewHolder.setOnCompanyItemClickListener(currentCompany, mCompanyItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mCompanies.size() - 1 && mIsLoadingIndicatorShow) ? LOADING.ordinal() : COMPANY.ordinal();
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    private int getListItemIdForType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        switch (type) {
            case HORIZONTAL:
                return R.layout.list_item_company_horizontal;
            default:
                return R.layout.list_item_company_vertical;
        }
    }

}
