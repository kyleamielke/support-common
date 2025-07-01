package io.thatworked.support.common.context.holder;

/**
 * Thread-local holder for request context.
 */
import io.thatworked.support.common.context.model.RequestContext;

public class RequestContextHolder {
    
    private static final ThreadLocal<RequestContext> CONTEXT_HOLDER = new ThreadLocal<>();
    
    private RequestContextHolder() {
        // Utility class
    }
    
    /**
     * Set the request context for the current thread.
     */
    public static void setContext(RequestContext context) {
        CONTEXT_HOLDER.set(context);
    }
    
    /**
     * Get the request context for the current thread.
     */
    public static RequestContext getContext() {
        return CONTEXT_HOLDER.get();
    }
    
    /**
     * Clear the request context for the current thread.
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
    
    /**
     * Get the current request ID.
     */
    public static String getRequestId() {
        RequestContext context = getContext();
        return context != null ? context.getRequestId() : null;
    }
    
    /**
     * Get the current correlation ID.
     */
    public static String getCorrelationId() {
        RequestContext context = getContext();
        return context != null ? context.getEffectiveCorrelationId() : null;
    }
    
    /**
     * Get the current user ID.
     */
    public static String getUserId() {
        RequestContext context = getContext();
        return context != null ? context.getUserId() : null;
    }
}