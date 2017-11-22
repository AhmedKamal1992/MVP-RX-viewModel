# MVP-RX-viewModel
An MVP project demonstrating the usage of the new google architecture components with the presenter

With google finally releasing the stable version of their architecture components weeks ago they recommended using the MVVM pattern as in thair provided repository.

after trying the new google architecting component and expriance how powerful they are.

While the MVVM with architecture components can save you a lot of work when it comes to configuration changes (ie: Screen rotation) i somehow feel like it needs more organization or at least thats what i think.

and because i really love MVP which had a problem compared to MVVM with the new google arch component and its that you need to handle all system configuation in the presenter like unsubscribing and disposing observables , unbinding views , etc.

so i decided that i can try to use the presenter with the new viewModel.
thus i created a sample demonstrating how to combine both (Unit tests will be added in the next commit).

and i will try to add more features in the future commits

Here is a diagram describing how this works and how does it survive the System oriantation.

1 - Before oriantation changes :

![alt text](https://i.imgur.com/2NbyPQa.png)

2 - After oriantation changes :

![alt text](https://i.imgur.com/MgZGe2a.png)

#- [Google Architecture Components (viewModel , liveData)]
#- [RxJava/RxAndroid/RxRelay/Lambda]
#- [Room for Presisting data]
#- [Dagger 2.12]
#- [Retrofit/OkHTTP]
#- [ButterKnife]
#- [HummerCast]
#- [Stetho]
#- [Leakcanary]
#- [Recyclerview/Cardview]

of course any kind of contribution is welcomed especially that i feel that the code can better.
