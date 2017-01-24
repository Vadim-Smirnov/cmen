package net.ginteam.carmen.provider.company;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;
import net.ginteam.carmen.network.api.service.CompanyService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriberWithMeta;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompaniesProvider {

    private static CompaniesProvider sInstance;

    private CompanyService mCompanyService;

    private CompaniesProvider() {
        mCompanyService = ApiManager.getInstance().getService(CompanyService.class);
    }

    public static CompaniesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new CompaniesProvider();
        }
        return sInstance;
    }

    public void fetchForCategory(final int categoryId, String filter, String sortField, String sortType,
                                 int page, final ModelCallbackWithMeta<List<CompanyModel>> completion) {
        mCompanyService
                .fetchCompanies(categoryId, filter, sortField, sortType, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriberWithMeta<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel, Pagination pagination) {
                        completion.onSuccess(resultModel, pagination);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchCompanyDetail(int companyId, final ModelCallback<CompanyModel> completion) {
        String relations = String.format("%s,%s,%s", ApiLinks.CATALOG.COMFORTS,
                ApiLinks.CATALOG.DETAIL, ApiLinks.CATALOG.CATEGORIES);

        mCompanyService
                .fetchCompanyDetail(companyId, relations)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<CompanyModel>() {
                    @Override
                    public void onSuccess(CompanyModel resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchRecentlyWatched(final ModelCallback<List<CompanyModel>> completion) {
        mCompanyService
                .fetchRecentlyWatched()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchPopular(int cityId, final ModelCallback<List<CompanyModel>> completion) {
        mCompanyService
                .fetchPopular(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
