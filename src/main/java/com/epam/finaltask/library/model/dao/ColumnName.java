package com.epam.finaltask.library.model.dao;

public final class ColumnName {
    /**
     * The constant ID and CREATED_AT fields for all tables
     */
    public static final String ID = "id";
    public static final String CREATED_AT = "created_at";

    /**
     * the constants for the users table
     */
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PASSPORT_NUMBER = "passport_number";
    public static final String USER_EMAIL = "email";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "role";
    public static final String USER_ADDRESS = "address";
    public static final String USER_BIRTH_DATE = "birth_date";
    public static final String USER_STATUS = "status";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_CREATED_AT = "user_created_at";

    /**
     * the constants for the books table
     */
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_GENRE_ID = "genre_id";
    public static final String BOOK_AUTHOR = "author";
    public static final String BOOK_DESCRIPTION = "description";
    public static final String BOOK_COPIES = "book_copies";
    public static final String BOOK_MAGE_ID = "image_id";
    public static final String BOOK_IMAGE_BOOK_URL = "image_url";
    public static final String BOOK_IMAGE_ID = "image_book_id";
    public static final String BOOK_CREATED_AT = "book_created_at";

    /**
     * the constants for the genre table
     **/

    public static final String GENRE_NAME = "genre_name";
    public static final String NAME = "name";


    /**
     * the constants for the images table
     **/

    public static final String IMAGE_URL = "url";
    public static final String IMAGE_BOOK_ID = "book_id";

    /**
     * the constants for the orders and order details table
     **/

    public static final String ORDER_USER_ID = "order_user_id";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_BOOK_ID = "book_id";
    public static final String ORDER_ISSUE_DATE = "issue_date";
    public static final String ORDER_RETURN_DATE = "return_date";
    public static final String ORDER_USAGE_DAYS = "usage_days";
    public static final String ORDER_TYPE = "type";
    public static final String ORDER_LAST_STATUS = "last_status";
    public static final String BAC_ID = "bac_id";
    public static final String BAC_AVAILABLE_COPIES = "available_copies";
    public static String BAC_BOOK_ID = "book_id";
    public static String AVAILABLE_COPIES = "available_copies";
    public static String ORDER_DETAILS_USER_ID = "user_id";
    public static String ORDER_DETAILS_ID = "order_details_id";
    public static String ORDER_DETAILS_STATUS = "order_details_status";
    public static String ORDER_DETAILS_CREATED_AT = "order_details_created_at";
    public static String ORDER_DETAILS_USER_STATUS = "user_status";
    public static String ORDER_DETAILS_COMMENT = "comment";

}
