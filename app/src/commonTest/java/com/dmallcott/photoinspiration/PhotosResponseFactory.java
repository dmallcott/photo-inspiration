package com.dmallcott.photoinspiration;

import com.dmallcott.photoinspiration.data.json.PhotosResponse;

public class PhotosResponseFactory {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PER_PAGE = 15;
    private static final int DEFAULT_TOTAL_RESULTS = 236;
    private static final String DEFAULT_PREVIOUS_PAGE = "http://api.pexels.com/v1/search/?page=0&per_page=15&query=example+query";
    private static final String DEFAULT_NEXT_PAGE = "http://api.pexels.com/v1/search/?page=2&per_page=15&query=example+query";

    public static PhotosResponse makePhotosResponse() {
        return makePhotosResponse(DEFAULT_PAGE);
    }

    public static PhotosResponse makePhotosResponse(final int page) {
        return PhotosResponse.create(
                page, DEFAULT_PER_PAGE, DEFAULT_TOTAL_RESULTS, DEFAULT_PREVIOUS_PAGE,
                DEFAULT_NEXT_PAGE, PhotoFactory.makePhotos(DEFAULT_PER_PAGE)
        );
    }
}
