package com.joboffersapi.domain.offercrud;


class OfferFacadeTestConfiguration {
    private final OfferFetchable inMemoryFetcherTestImpl;
    private final OfferRepository offerRepository;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcherTestImpl = new InMemoryFetcherTestImpl(
                "[\n" +
                        "  {\n" +
                        "    \"title\": \"Software Engineer\",\n" +
                        "    \"company\": \"Tech Company\",\n" +
                        "    \"salary\": 11.11,\n" +
                        "    \"offerUrl\": \"https://techcompany.com/jobs/1\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"Data Scientist\",\n" +
                        "    \"company\": \"Data Company\",\n" +
                        "    \"salary\": 22.22,\n" +
                        "    \"offerUrl\": \"https://datacompany.com/jobs/2\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"title\": \"SOFTŁER INDŻINIER\",\n" +
                        "    \"company\": \"TEK KOMPANY\",\n" +
                        "    \"salary\": 123.12,\n" +
                        "    \"offerUrl\": \"https://WuWuWu.TESTUEREL.KOM/DZOP/3\"\n" +
                        "  } " +
                "]"
        );
        this.offerRepository = new InMemoryOfferRepository();
    }

    public OfferFacade getOfferFacadeForTests() {
        return new OfferFacade(new OfferService(offerRepository, inMemoryFetcherTestImpl));
    }

    void clearDatabase() {
        offerRepository.deleteAll();
    }
}
