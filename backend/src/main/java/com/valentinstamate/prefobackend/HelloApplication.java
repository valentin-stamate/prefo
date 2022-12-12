package com.valentinstamate.prefobackend;

import com.valentinstamate.prefobackend.filters.binding.ApplicationCors;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationCors
@ApplicationPath("/api")
public class HelloApplication extends Application { }