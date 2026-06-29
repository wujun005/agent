# Product API Curl

## Start service

If the application is not running yet:

```bash
mvn spring-boot:run
```

The examples below assume:

- Base URL: `http://localhost:8080`
- API path: `/api/products`

## List products

```bash
curl 'http://localhost:8080/api/products'
```

Example response:

```json
{
  "code": 200,
  "msg": "success",
  "records": [
    {
      "id": 1,
      "productName": "iPhone 15",
      "description": "128G",
      "price": 5999.00,
      "stock": 10,
      "status": 1,
      "createdAt": "2026-05-30T10:00:00",
      "updatedAt": "2026-05-30T10:00:00"
    }
  ],
  "total": 1
}
```

## List products by name

```bash
curl --get 'http://localhost:8080/api/products' \
  --data-urlencode 'productName=手机'
```

## Get product by id

```bash
curl 'http://localhost:8080/api/products/1'
```

## Create product

```bash
curl -i -X POST 'http://localhost:8080/api/products' \
  -H 'Content-Type: application/json' \
  -d '{
    "productName": "iPhone 15",
    "description": "128G",
    "price": 5999.00,
    "stock": 10,
    "status": 1
  }'
```

## Update product

```bash
curl -i -X PUT 'http://localhost:8080/api/products/1' \
  -H 'Content-Type: application/json' \
  -d '{
    "productName": "iPhone 15 Pro",
    "description": "256G",
    "price": 7999.00,
    "stock": 8,
    "status": 1
  }'
```

## Delete product

```bash
curl -i -X DELETE 'http://localhost:8080/api/products/1'
```

## Validation notes

- `productName` is required.
- `price` is required and must be `>= 0`.
- `stock` is required and must be `>= 0`.
- `status`, if provided, must be `0` or `1`.
