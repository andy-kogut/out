    @Test
    @PactTestFor(pactMethod = "createPact", pactVersion = PactSpecVersion.V4)
    void runConsumerTest(MockServer mockServer) throws IOException, org.apache.hc.core5.http.ParseException {
        String baseUrl = mockServer.getUrl();
        DummyInstrumentClient client = new DummyInstrumentClient(baseUrl);
        String response = client.getInstrument();

        // Validate response is not null or empty
        assertNotNull(response, "Response should not be null");
        assertTrue(response.length() > 0, "Response should not be empty");
        
        // Validate response contains expected top-level structure
        assertTrue(response.contains("\"Entities\""), "Response should contain Entities key");
        assertTrue(response.contains("FullInstrument"), "Response should contain FullInstrument");
        
        // Validate response is valid JSON structure (starts with { and contains Entities array)
        assertTrue(response.trim().startsWith("{"), "Response should be a JSON object");
        assertTrue(response.contains("["), "Response should contain at least one array");
        
        // Validate that the mock server returned the expected structure
        // This ensures the PactBodyBuilder correctly generated the contract
        assertTrue(response.contains("InstrumentDetails") || response.contains("AllInstrumentRegulations"), 
                   "Response should contain expected instrument fields");
    }
