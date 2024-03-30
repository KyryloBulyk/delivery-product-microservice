# 📦 Delivery Product Microservice

## 📌 Overview

The Delivery Product Microservice 📦 is a key component of the **Delivery** microservices architecture, focusing on managing product data. This includes handling product creation 🛠️, updating 🔄, retrieval 🔍, and deletion 🗑️. It is essential for managing the catalog of products and services within the Delivery application.

## 🌐 Architecture Interaction

As a vital part of the Delivery microservices ecosystem, the Product Microservice interacts with several other services to offer a seamless operational flow:

- [Delivery API Gateway](https://github.com/KyryloBulyk/delivery-api-gateway) 🚪: Serves as the entry point, directing product-related requests to this microservice and facilitating the communication between various microservices.

- [Delivery Configuration](https://github.com/KyryloBulyk/delivery-configuration) ⚙️: Manages centralized configurations, allowing the Product Microservice to adjust dynamically to the changing configurations.

- [Delivery Discovery](https://github.com/KyryloBulyk/delivery-discovery) 🔍: Ensures efficient service discovery, enabling seamless interaction and communication with other services in the ecosystem.

- [Delivery Users Microservice](https://github.com/KyryloBulyk/delivery-users) 👤: Manages user data, registration, authentication, and user profile management. The API Gateway authenticates user credentials and forwards user-centric requests to this service.


This setup allows the Product Microservice to operate effectively, ensuring the consistent and reliable management of product data within the Delivery application.

## 🚀 Features

- **Category Management**: Facilitates the organization of products by allowing the creation, update, retrieval, and deletion of product categories. This feature ensures products are systematically classified, making them easier to manage and navigate within the application.
- **Product Management**: Provides comprehensive control over product entries, including adding new products, updating existing product details, retrieving product information, and deleting products from the catalog. This is crucial for keeping the product database current and accurate.
- **Swagger Documentation**: Provides extensive API documentation available through [Swagger UI](http://localhost:8080/swagger-ui/), detailing the endpoints and their functionalities.

## 📦 Running via Docker Compose

To run the Product Microservice as a part of the overall Delivery application, Docker Compose is used in conjunction with the Delivery API Gateway, facilitating an integrated service launch:

1. Ensure Docker 🐳 and Docker Compose are installed on your machine.
2. Clone the repository for the Delivery API Gateway that contains the `docker-compose.yml` file.
3. Execute the following command at the root of the cloned repository:

   ```bash
   docker-compose up
   ```
This command will initialize all the necessary microservices, including the Product Microservice, as per the Docker Compose configuration.

## 🤝 Contributing

Contributions to improve the Users Microservice or the Delivery application as a whole are highly appreciated. Whether it's enhancing features, fixing bugs, or improving documentation, your contributions are welcome. Please check the project's GitHub repository for contribution guidelines.