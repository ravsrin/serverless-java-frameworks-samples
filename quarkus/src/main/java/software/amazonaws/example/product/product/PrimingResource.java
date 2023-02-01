// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

/**
 * Prime the DynamoDB client for the SnapStart example. This file can be excluded if not using SnapStart.
 * To learn more about SnapStart and Priming, refer to
 * https://aws.amazon.com/blogs/compute/reducing-java-cold-starts-on-aws-lambda-functions-with-snapstart/
 **/

package software.amazonaws.example.product.product;

import io.quarkus.runtime.Startup;
import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import software.amazonaws.example.product.product.dao.DynamoProductDao;

import javax.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class PrimingResource implements Resource {

  private final DynamoProductDao productDao;

  public PrimingResource(DynamoProductDao productDao) {
    this.productDao = productDao;
    Core.getGlobalContext().register(this);
  }

  @Override
  public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
    System.out.println("beforeCheckpoint hook");
    // Initialize the AWS SDK DynamoDBClient class with the "Priming" technique.
    productDao.describeTable();
  }

  @Override
  public void afterRestore(Context<? extends Resource> context) throws Exception {
    System.out.println("afterRestore hook");
  }
}