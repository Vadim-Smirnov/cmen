package net.ginteam.carmen.kotlin.contract

import android.support.annotation.StringRes
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */
object BaseCompaniesContract {

    interface View : BaseContract.View {

        fun showCompanies(companies: MutableList <CompanyModel>, pagination: PaginationModel? = null)
        fun showFavoriteConfirmationMessage(company: CompanyModel, @StringRes messageResId: Int)
        fun showFavoriteErrorMessage(@StringRes messageResId: Int)

    }

    interface Presenter<in V : View> : BaseContract.Presenter <V> {

        fun addCompanyToFavorites(company: CompanyModel)
        fun removeCompanyFromFavorites(company: CompanyModel)

    }

}

object CompaniesContract {

    interface View : BaseCompaniesContract.View

    interface Presenter : BaseCompaniesContract.Presenter <View> {

        fun fetchCompanies(categoryId: Int, filterQuery: String = "",
                           sortField: String = "", sortType: String = "", pageNumber: Int = 1)

        fun resetFilters()

    }

}

object PopularCompaniesContract {

    interface View : BaseCompaniesContract.View

    interface Presenter : BaseCompaniesContract.Presenter <View> {

        fun fetchPopularCompanies()
        fun getUserCityName(): String

    }

}

object RecentlyWatchedCompaniesContract {

    interface View : BaseCompaniesContract.View

    interface Presenter : BaseCompaniesContract.Presenter <View> {

        fun fetchUserRecentlyWatched()

    }

}

object FavoriteCompaniesContract {

    interface View : BaseCompaniesContract.View

    interface Presenter : BaseCompaniesContract.Presenter <View> {

        fun fetchUserFavorites(pageNumber: Int)

    }

}