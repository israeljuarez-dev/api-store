package com.app.store_api.controller.resource;

import com.app.store_api.dto.criteria.SearchProductCriteriaDTO;
import com.app.store_api.dto.product.ProductDTO;
import com.app.store_api.exception.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "product",
        description = "Operations related with product entity (Base URL: localhost/api/v1/products)"
)
public interface ProductResource {

    @Operation(
            summary = "Get list of products with filters and pagination",
            description = """
            Retrieves a paginated list of products based on the provided search criteria.
            You can filter by name, trade mark, price, and creationDate.
            You can also specify the page number, page size, and sort options.
        """,
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "name",
                            description = "Filter by product name",
                            example = "Laptop"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "tradeMark",
                            description = "Filter by trade mark",
                            example = "Lenovo"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "price",
                            description = "Filter by product price",
                            example = "1200.00"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "creationDate",
                            description = "Filter by creation date (yyyy-MM-dd)",
                            example = "2024-06-01"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "sortField",
                            description = "Field to sort by (e.g., 'price')",
                            example = "price"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "sortingDirection",
                            description = "Sort direction: 'asc' or 'desc'",
                            example = "asc"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "pageActual",
                            description = "Page number (starts at 0)",
                            example = "0"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "pageSize",
                            description = "Number of records per page",
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the list of products",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    ResponseEntity<List<ProductDTO>> getProducts(SearchProductCriteriaDTO criteriaDTO);

    @Operation(
            summary = "Get a single product by ID",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            description = "Product ID",
                            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the product",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    ResponseEntity<ProductDTO> getProductById(@PathVariable("id") UUID id);

    @Operation(
            summary = "Create a new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "New Product",
                                            summary = "Example product",
                                            value = """
                        {
                          "name": "Mouse",
                          "tradeMark": "Logitech",
                          "price": 25.50,
                          "quantityAvailable": 100
                        }
                        """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productDTO);

    @Operation(
            summary = "Update an existing product",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Updated Product",
                                            summary = "Updated example",
                                            value = """
                        {
                          "name": "Keyboard",
                          "tradeMark": "Logitech",
                          "price": 45.00,
                          "quantityAvailable": 200
                        }
                        """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") UUID id, @RequestBody @Valid ProductDTO productDTO);

    @Operation(
            summary = "Delete a product",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            description = "Product ID",
                            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    ResponseEntity<Void> deleteProduct(@PathVariable UUID id);
}