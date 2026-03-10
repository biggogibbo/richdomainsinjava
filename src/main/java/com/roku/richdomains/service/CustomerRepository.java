package com.roku.richdomains.service;

import com.roku.richdomains.domain.Customer;

public interface CustomerRepository {
  Customer findById(String customerId);
}
