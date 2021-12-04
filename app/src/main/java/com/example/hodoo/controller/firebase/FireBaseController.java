package com.example.hodoo.controller.firebase;


import com.example.hodoo.controller.CommentInterface;
import com.example.hodoo.controller.MessageInterface;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.PostSuggestionInterface;
import com.example.hodoo.controller.UserAuthInterface;

public class FireBaseController extends FireBaseProxy
        implements UserAuthInterface, PostInterface,
        MessageInterface, CommentInterface, PostSuggestionInterface {



}
