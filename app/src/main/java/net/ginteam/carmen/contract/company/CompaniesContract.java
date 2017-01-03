package net.ginteam.carmen.contract.company;

import net.ginteam.carmen.contract.FetchContract;
import net.ginteam.carmen.model.company.CompanyModel;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public interface CompaniesContract {

    interface View extends FetchContract.View {

        void showCompanies(List<CompanyModel> companies);

    }

    interface Presenter extends FetchContract.Presenter <View> {

        void selectCompany(CompanyModel company);

    }

}