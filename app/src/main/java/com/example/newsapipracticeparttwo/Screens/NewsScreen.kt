package com.example.newsapipracticeparttwo.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapipracticeparttwo.R
import com.example.newsapipracticeparttwo.model.ArticleEntity
import com.example.newsapipracticeparttwo.viewmodels.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val articles by viewModel.articles.collectAsStateWithLifecycle()


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            // 2. Use 'itemsIndexed' to detect the scroll position
            itemsIndexed(articles) { index, article ->
                NewsItem(article = article)
                if (index == articles.lastIndex) {
                    LaunchedEffect(Unit) {
                        viewModel.loadMore()
                    }
                }
            }
        }

        // Optional: Show a loading spinner in the center if list is empty
        if (articles.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun NewsItem(article: ArticleEntity) {
    val context = LocalContext.current
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                val url = article.url

                if(url.isNotEmpty()){
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }


            }
            .padding(vertical = 8.dp)// Add spacing between cards
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Image Handler
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Title Handler
            Text(
                text = article.title ?: "No Title",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
