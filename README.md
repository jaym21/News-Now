# News-Now
- News Now is an android app made using Kotlin to get latest news to the user.
- It uses MVVM Architecture and Room Database.
- The app implementes Pagination.
- Retrofit2 is used for API response.
- Recycler View is used to display the news.
- Glide Library is used to display the image of news.

![latestnews_activity](https://user-images.githubusercontent.com/48640844/106473814-3f1d7a80-64ca-11eb-8078-aaf8b55cc17e.png)

- User can click on any news to read the full article from the source website inside the app.

![article_webview](https://user-images.githubusercontent.com/48640844/105638771-ab7cf600-5e9a-11eb-9e1f-35871f8c02f2.png)

- User can save news articles(using floating save button in bottom right corner of article) to read them later and can delete them by swiping article off. 

![saved_activity](https://user-images.githubusercontent.com/48640844/106473826-42186b00-64ca-11eb-8e19-f4a8d54329f5.png)

## Dependencies
### UI
- implementation "androidx.cardview:cardview:1.0.0"
- implementation 'com.google.android.material:material:1.2.1'
- implementation 'androidx.recyclerview:recyclerview:1.1.0'
- implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
- implementation 'androidx.navigation:navigation-fragment-ktx:2.3.2'
- implementation 'androidx.navigation:navigation-ui-ktx:2.3.2'

### Glide
- implementation 'com.github.bumptech.glide:glide:4.11.0'

### Retrofit2
- implementation 'com.squareup.retrofit2:retrofit:2.9.0'
- implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

### Room
 - implementation "androidx.room:room-ktx:2.2.6"
 - kapt "androidx.room:room-compiler:2.2.6"
 - androidTestImplementation "androidx.room:room-testing:2.2.6"
 
 ### Lifecycle Componenets
 - implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
 - implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
 - implementation "androidx.lifecycle:lifecycle-common-java8:2.2.0"
