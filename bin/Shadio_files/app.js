const HTML_NEWLINE = "<br>";
const API_KEY = "AIzaSyAkM6u80hmee7gy65SF4sHPyUIrdlGMsgQ";

function init()
{
    gapi.client.setApiKey(API_KEY);
    gapi.client.load("youtube", "v3", function() {});
}

function submitSongQuery() 
{
    var songTitle = document.getElementById("songTitle").value;
    var songArtist = document.getElementById("songArtist").value;
    var songQualifier = document.getElementById("songQualifier").value;

    if (songTitle.length == 0) 
    {
        alert("Please provide a song name so we can help find the appropriate song for you!");
    }
    else if (songArtist.length == 0)
    {
        alert("Please provide a song artist so we can help find the appropriate song for you!");
    }
    else 
    {
        console.log("hello");
        getTopYoutubeResult(5, songTitle + " " + songArtist + " " + songQualifier);
    }
}

function getTopYoutubeResult(numResults, query) 
{
    var request = gapi.client.youtube.search.list
    (
        {
        part: "snippet",
        type: "video",
        q: query,
        maxResults: numResults
        }
    ); 

    request.execute(function(response) 
    {
        var result = response.result;
        $.get("searchresult.html", function(data)
        {   
            $("#searchResults").empty();
            $.each(result.items, function(index, item) 
            {
                $("#searchResults").append(jsonKeyReplace(data, [{"title": item.snippet.title, "videoId": item.id.videoId}]));
            });
        });
    });
}

function addSongToQueue(songTitle, videoId) 
{
    //add to queue
    $.get("songqueueitem.html", function(data)
    {
        console.log(songTitle);
        $(document).ready(function () 
        {
            $("#songQueue tr:last").after(jsonKeyReplace(data, [{"songTitle": songTitle, "videoId": videoId }]));
        });
    });
    $("#searchResults").empty();
}

function displayYoutubeVideo(videoId) 
{
    //prepare request
    var request = gapi.client.youtube.search.list
    (
        {
        part: "snippet",
        type: "video",
        q: videoId,
        maxResults: 1
        }
    ); 
    //execute request
    request.execute(function(response) 
    {
        var result = response.result;
        $.each(result.items, function(index, item) 
        {
            $.get("videodisplay.html", function(data)
            {
                $("#currentSong").empty();
                $("#currentSong").append(jsonKeyReplace(data, [{"title": item.snippet.title, "videoId": item.id.videoId}]));
            });
        });
    });
}

function jsonKeyReplace(e,t)
{
    res = e;
    for(var n = 0; n < t.length; n++)
    {
        res = res.replace(/\{\{(.*?)\}\}/g, function(e,r)
        {
            return t[n][r]
        })
    }
    return res 
}