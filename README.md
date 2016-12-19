# Basics of RxJava in Android

ReactiveX extends the observer pattern to support sequences of data and/or events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety, concurrent data structures, and non-blocking I/O.

# Observable

In ReactiveX an observer subscribes to an Observable. Then that observer reacts to whatever item or sequence of items the Observable emits. This pattern facilitates concurrent operations because it does not need to block while waiting for the Observable to emit objects, but instead it creates a sentry in the form of an observer that stands ready to react appropriately at whatever future time the Observable does so.

# onNext, onCompleted, and onError

The Subscribe method is how you connect an observer to an Observable. Your observer implements some subset of the following methods:

**onNext** -
An Observable calls this method whenever the Observable emits an item. This method takes as a parameter the item emitted by the Observable.

**onError** -
An Observable calls this method to indicate that it has failed to generate the expected data or has encountered some other error. It will not make further calls to onNext or onCompleted. The onError method takes as its parameter an indication of what caused the error.

**onCompleted** -
An Observable calls this method after it has called onNext for the final time, if it has not encountered any errors.

[Read this page](http://reactivex.io/documentation/operators.html) for more detail.

# RxJava for Android by example

In this tutorial, there are simple and complex examples which try to learn how to use RxJava in Android.

Tutorials included number of examples which describe,

 - How to use Creating Observables
 - How to use Transforming Observables
 - How to use Filtering Observables
 - How to use Combining Observables

Moreover, there is an example of How to download file by RxJava without use of native AsyncTask.

# Contributing

 - Do you have a new feature in mind?
 - Do you know how to improve existing docs or code?
 - Have you found a bug?

 I try to explain basics of RxJava in Android. If you have similar examples for other users, feel free to send pull request.
 Contributors are always welcome! Be ready to share your code and help out others.

# License

Copyright 2016 Harsh Patel

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.



