package com.godohdev.latihanrxjava

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.godohdev.latihanrxjava.data.model.Price
import com.godohdev.latihanrxjava.data.model.ResponseFlights
import com.godohdev.latihanrxjava.data.network.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

/**
 *
 * Created by Wahyu Permadi on 11/03/20.
 * Android Engineer
 *
 **/

class MainViewModel (
    private val apiService: ApiService
) : ViewModel(){

    private val disposable = CompositeDisposable()
    private var _ticketsFlight = MutableLiveData<ArrayList<ResponseFlights>>()
    val ticketsFlights : LiveData<ArrayList<ResponseFlights>>
        get() = _ticketsFlight

    private var _priceFlight = MutableLiveData<HashMap<Int, ResponseFlights>>()
    val priceFlight : LiveData<HashMap<Int, ResponseFlights>>
        get() = _priceFlight

    fun fetchTicketList(
        from: String,
        to: String
    ) : ConnectableObservable<List<ResponseFlights>>? {
        return getTickets(from, to)?.replay()
    }

    /**
     * Making Retrofit call to fetch all tickets
     */
    private fun getTickets(
        from: String,
        to: String
    ): Observable<List<ResponseFlights>>? {
        return apiService.searchTickets(from, to)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchAllTickets(observableData: ConnectableObservable<List<ResponseFlights>>){
        disposable.add(
            observableData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _ticketsFlight.value = it as ArrayList<ResponseFlights>?
                },{
                    Log.d("DATA_ERROR","DATA ERROR "+it.localizedMessage)
                })
        )

        disposable.add(
            observableData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap {
                    Observable.fromIterable(it)
                }
                .flatMap {
                    getPriceObservable(it)
                }
                .subscribe({
                    val position = _ticketsFlight.value?.indexOf(it)
                    position?.let {
                            it1 ->
                        it?.let {
                                it2 ->
                            val hasMap = HashMap<Int, ResponseFlights>()
                            hasMap[it1] = it2
                            _priceFlight.value = hasMap
                        }
                    }
                },{
                    Log.d("DATA_ERROR","DATA ERROR "+it.localizedMessage)
                })
        )
        observableData.connect()
    }

    /**
     * Making Retrofit call to get single ticket price
     * get price HTTP call returns Price object, but
     * map() operator is used to change the return type to Ticket
     */
    private fun getPriceObservable(ticket: ResponseFlights): Observable<ResponseFlights?>? {
        return apiService
            .getPrice(ticket.flightNumber!!, ticket.from!!, ticket.to!!)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                ticket.price = it
                ticket

            }
    }
}