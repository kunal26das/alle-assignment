# Android Assignment

Below are the actual screens for the ‘Screenshot’ tab and ‘Screenshot Details’ page. We aim to create an experience where there is a horizontal image strip at the bottom with a full image preview of the selected image.

Please build a working demo for this flow on Android. Implement the logic to auto-sync the images from the screenshot gallery. Use Google's ML Kit for text recognition to populate the description field with OCR data from the image. The collections should be the labels from on-device image labeling.

The design references can be adapted from the provided iOS screens:

[13 Pro - 326.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/06897713-025f-42ae-8097-06c29894db06/13_Pro_-_326.png)

[13 Pro - 327.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/716207ba-320f-45f1-9fb8-e849b25f44d3/13_Pro_-_327.png)

[13 Pro - 329.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/459cce6b-58a7-4ec1-a2c2-68107f48aff5/13_Pro_-_329.png)

[13 Pro - 330.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/95bbc61e-4386-415f-a29a-08dc9c60a45b/13_Pro_-_330.png)

[13 Pro - 331.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e1c284b0-5a68-4ddb-95c7-c1b88c3a9dae/13_Pro_-_331.png)

Expectations:

1. The app should efficiently handle a large number of screenshots, avoiding memory-related issues. Use a dataset for a large number of images if needed, which can be downloaded using the following command:
    
    ```bash
    
    wget http://data.vision.ee.ethz.ch/cvl/DIV2K/DIV2K_train_HR.zip
    ```
    
    To test OCR, use some screenshots with text on them.
    
2. Ensure the UI is responsive. Implement loading states where applicable.
3. Support fast scrolling on the bottom thumbnail image strip.
4. Implement all functionalities
5. Support min API Level 21 and higher, and the latest version as well.
