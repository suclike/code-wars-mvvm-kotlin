package com.github.fatihsokmen.codewars.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.fatihsokmen.codewars.data.ChallengeDomain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ChallengesViewModel constructor(
        challengesDataSourceFactory: DataSource.Factory<Int, ChallengeDomain>
) : ViewModel() {

    private val subscriptions: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    var challenges: LiveData<PagedList<ChallengeDomain>>

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(2)
                .setInitialLoadSizeHint(0)
                .setEnablePlaceholders(true)
                .build()
        challenges = LivePagedListBuilder<Int, ChallengeDomain>(
                challengesDataSourceFactory, config).build()
    }

    override fun onCleared() {
        subscriptions.clear()
    }

    class Factory @Inject constructor(
            private val dataSourceFactory: DataSource.Factory<Int, ChallengeDomain>)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(ChallengesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChallengesViewModel(dataSourceFactory) as T
            }
            throw IllegalArgumentException("Unsupported ViewModel class")
        }
    }
}