# Controller

## DealController

The `DealController` handles HTTP requests for uploading transaction files.

### Endpoints

#### POST /api/deals/upload

Uploads a file containing transaction data.

- **Request Parameters**:
    - `file` (MultipartFile): The file to be uploaded.

- **Responses**:
    - `201 CREATED`: File uploaded and deals saved successfully.
    - `400 BAD REQUEST`: File is empty or failed to upload and parse.
    - `500 INTERNAL SERVER ERROR`: An error occurred during file upload and parsing.


