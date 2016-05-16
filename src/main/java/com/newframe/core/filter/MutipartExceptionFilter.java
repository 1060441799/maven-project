package com.newframe.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class MutipartExceptionFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(MutipartExceptionFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain arg2)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			arg2.doFilter(request, response);
		} catch (ServletException e) {
			if (e.getRootCause() instanceof MaxUploadSizeExceededException) {
				handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
			} else {
				throw e;
			}
		}
	}

	private void handle(HttpServletRequest request, HttpServletResponse response,
			MaxUploadSizeExceededException rootCause) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (log.isDebugEnabled()) {
			log.debug("Catch upload issue.");
		}
		IOUtils.write("{result:\"Failed\"}", response.getOutputStream());
	}

}
