package ru.gdgkazan.simpleweather.network;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gdgkazan.simpleweather.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class ApiFactoryCitiesList {

    private static OkHttpClient sClient;

    private static volatile CitiesListService sService;

    private ApiFactoryCitiesList() {
    }

    @NonNull
    public static CitiesListService getCitiesListService() {
        CitiesListService service = sService;
        if (service == null) {
            synchronized (ApiFactoryCitiesList.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(CitiesListService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HELP_LIST)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactoryCitiesList.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }
}
