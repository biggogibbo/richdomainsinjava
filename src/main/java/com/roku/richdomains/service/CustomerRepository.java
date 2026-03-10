package com.roku.richdomains.service;

import com.roku.richdomains.domain.Customer;
import com.roku.richdomains.domain.CustomerId;

public interface CustomerRepository {
  Customer findById(CustomerId customerId);
}
