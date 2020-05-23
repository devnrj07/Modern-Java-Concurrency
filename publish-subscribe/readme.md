# Exploring the publish-subscribe framework

Java 9 supports the Reactive Streams initiative by providing a publish-subscribe framework (also known as the Flow API) that consists of the `java.util.concurrent.Flow` and `java.util.concurrent.SubmissionPublisher` classes.

Flow is a repository for four nested static interfaces whose methods establish flow-controlled components in which publishers produce data items that are consumed by one or more subscribers:

* Publisher: A producer of data items that are received by subscribers.
* Subscriber: A receiver of data items.
* Subscription: Linkage between a Publisher and a Subscriber.
* Processor: A combination of Publisher and Subscriber for specifying a data-transformation function.

# Reactive Programming

Reactive programming is about processing an asynchronous stream of data items, where applications react to the data items as they occur. A stream of data is essentially a sequence of data items occurring over time. This model is more memory efficient because the data is processed as streams, as compared to iterating over the in-memory data.

 

In the Reactive Programming model, there is a Publisher and a Subscriber. The Publisher publishes a stream of data, to which the Subscriber is asynchronously subscribed.

 

The model also provides a mechanism to introduce higher order functions to operate on the stream by means of Processors. Processors transform the data stream without the need for changing the Publisher or the Subscriber. The Processor (or a chain of Processors) sit between the Publisher and the Subscriber to transform one stream of data to another. The Publisher and the Subscriber are independent of the transformation that happen to the stream of data.

# CompletableFuture enhancements

Java 8 introduced the CompletableFuture<T> class, which is a java.util.concurrent.Future<T> that may be explicitly completed (setting its value and status), and may be used as a java.util.concurrent.CompletionStage, supporting dependent functions and actions that are triggered upon the future's completion. Java 9 introduces several enhancements to CompletableFuture:

    support for delays and timeouts
    improved support for subclassing
    new factory methods
