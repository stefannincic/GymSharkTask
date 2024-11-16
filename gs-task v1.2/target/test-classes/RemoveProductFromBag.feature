Feature: Bag

  Scenario: User removes a product from their Bag
    Given there are products in the bag
    When remove a product from the bag
    Then the product is no longer in the bag

  Scenario: User increases the product quantity in their Bag
    Given there are products in the bag
    When increase the quantity of the product on 3
    Then the quantity of the product in the bag is increased on 3

  Scenario: User decreases the product quantity in their Bag
    Given there are products in the bag
    And the selected product quantity is greater than one and it is 3
    When decrease the quantity of the product on 2
    Then the quantity of the product in the bag is decreased on 2
