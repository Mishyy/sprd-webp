# sprd-webp
sprd-webp is a Java wrapper around Google's libwebp for generating webp encoded images. It is inspired by [webp project of Luciad](https://bitbucket.org/luciad/webp-imageio).

# Prerequirements
## Google WebP library
Use the shipped native libwebp library for Linux or macOS [here](src/main/resources/natives) or download the latest precompiled version for your OS from [https://developers.google.com/speed/webp/](https://developers.google.com/speed/webp/docs/precompiled#getting_cwebp_dwebp_and_the_webp_libraries) and put it into the META-INF/natives folder.
If you need a shared library (as shipped by that project) clone [github mirror of webm project](https://github.com/webmproject/libwebp)
```
cd libwebp
make -f makefile.unix
cd swig
gcc -DPIC -shared -fPIC -arch arm64 -fno-strict-aliasing -O2 libwebp_java_wrap.c -lwebp -o libwebp_jni.so    
```
Loading and managing the native library stuff at runtime is handled by [native-lib-loader](https://github.com/scijava/native-lib-loader).
For more informations behind it and the required folder structure see the [readme](https://github.com/scijava/native-lib-loader/blob/master/README.md) of that project

## Java JNI bindings
The origin libwebp source includes a precompiled jar file which contains the native JNI bindings for libwebp library.
It is referenced in central pom.xml file of sprd-webp project as 'com.google.webp|webp'

Install this jar library into your local (or company) maven repository
```
mvn install:install-file -Dfile=libwebp.jar -DgroupId=com.google.webp -DartifactId=webp -Dversion=5.0.0 -Dpackaging=jar
```
