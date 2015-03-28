# Code example #
In this page you can see some examples of usage of the cUml code

[docs](http://cuml2svg.googlecode.com/files/relazione.pdf)

## Model example ##
File test.u2sm
```
package org.cc{
  class Class1{
    attributes{
      public attribute1;
      public attribute2;
    }
    methods{
      private method1(int arg1);
      public method2(void arg);
    }
  }
}
```

## Layout example ##
File test.u2sl
```
import text.u2sm
[
  [
    @hide-args
    (class org.cc.Class1)
  ]
]

```