## So how do I use this thing?

I'm so glad you asked!

There are three points of entry into this API: initialization, execution and configuration.

## Initialization
First and foremost, you **absolutely must** call `ThreadLocalDns.initialize()` in your application before any DNS lookups are performed within your JVM instance.  If you fail to do this, then Java will initialize the default DNS implementation and you won't be able to override anything at runtime.

## Execution
This is your main point of access for using the API.  In order to work with solutions that spawn thread pools (for instance, a proxy server), thread-local-dns provides overridden DNS settings in an `InheritableThreadLocal`.  As a result, you'll need to provide a context of what you want to run within that scope.

The code you need to write looks like this:

``` java
ThreadLocalDns.executeContext(dnsConfiguration, new ThreadLocalDnsContext() {
  public void execute() {
    // your code goes here
  }
});
```
## Configuration
In order to create the `dnsConfiguration` argument, you should use `DnsConfigurationBuilder` by importing it statically.

``` java
...
import static com.theotherian.dns.DnsConfigurationBuilder.*
...

public class MyApplication {

  ...

  public void doSomething() {
    ThreadLocalDnsConfiguration dnsConfiguration = newBuilder().map(hosts("www.somehost.com", "www.someotherhost.com"),
      to("127.0.0.1")).map(hosts("www.anotherhost.org"), to("127.0.0.2")).build();
    ...
    ThreadLocalDns.executeContext(dnsConfiguration, new ThreadLocalDnsContext() {
      public void execute() {
        // your code goes here
      }
    });
  }

  ...

}
```