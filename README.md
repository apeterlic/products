# Getting Started

For guidelines on how to start the application locally, please refer to the
/documentation/START_GUIDE.md document.

### Assumptions:

1. The application is created for a single client/organization.
2. Endpoints are created for the frontend component.
3. Within one order, each product will have exactly one order item. Currently, quantity checks will
   not work correctly if an order contains multiple order items referencing the same product (this
   could be easily solved by grouping order items).
4. All data in the product (except id and created_at) are modifiable.
5. When the product is deleted, the soft deletion is performed.
5. Orders cannot be updated or removed.

### Authentication Proposal:

1. Authentication uses Open Authorization (OAuth 2.0) — login via SSO (e.g., Keycloak); 
   communication between frontend and backend done by passing a JWT Token.
2. Each user should belong to a specific client/organization.
3. Define specific roles and permissions.

### Ensuring Redundancy:

1. Expose endpoints that provide useful runtime information about the application, such as health
   checks. Spring Actuator offers such endpoints. They can be configured in cloud environments - for
   example, Kubernetes' liveness and readiness probes.
2. Ensure proper load balancing for components that experience heavy traffic, perform expensive
   batch jobs, or similar tasks. OpenShift provides a Horizontal Pod Autoscaler (HPA), which can be
   configured to create new pods when the current ones are overwhelmed.
3. Implement multithreading if needed to execute multiple tasks simultaneously.
4. Provide regular data backups.
5. Use a replicated database to access some data if needed.
6. Index columns to speed up data retrieval.

### Possible Improvements:

1. Include caching for resources (currencies and categories).
2. Introduce a new ‘description’ column in the currency and category tables.
3. Calculate the total price of a product using the provided VAT.
4. Make the ‘stockQuantity’ field mandatory only for PHYSICAL products.
5. Expose an endpoint that checks product quantity when a product is added to the cart. Some locking
   mechanism would be needed for the requested product quantity.
6. Once the application is ready to be deployed, define environment variables for configuration
   data (from application.yml).
~~7. For date fields ‘from’ and ‘to’: ‘to’ cannot be before ‘from’, and neither can be in the future.~~