/*
 * Copyright (c) 2012 Aldebaran Robotics. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be
 * found in the COPYING file.
 */
#include <gtest/gtest.h>
#include <stdexcept>

#include <almath/types/alpose2d.h>
#include <almath/tools/altrigonometry.h>


TEST(ALPose2DTest, basicOperator)
{
  AL::Math::Pose2D pPos2D  = AL::Math::Pose2D();
  AL::Math::Pose2D pPos2D1 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  AL::Math::Pose2D pPos2D2 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);

  // operator !=
  EXPECT_FALSE(pPos2D1 != pPos2D2);
  EXPECT_TRUE(pPos2D1 != AL::Math::Pose2D());

  // operator ==
  EXPECT_TRUE(pPos2D1 == pPos2D2);
  EXPECT_FALSE(pPos2D1 == AL::Math::Pose2D());

  // operator + (a+b)
  pPos2D1 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D2 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D = pPos2D1 + pPos2D2;
  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(1.0f, -0.6f, 0.2f), 0.001f));

  // operator + (+a)
  pPos2D1 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D  = AL::Math::Pose2D();
  pPos2D = +pPos2D1;
  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(0.5f, -0.3f, 0.1f), 0.001f));

  // operator +=
  pPos2D1 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D2 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D1 += pPos2D2;
  EXPECT_TRUE(pPos2D1.isNear(AL::Math::Pose2D(1.0f, -0.6f, 0.2f), 0.001f));

  pPos2D1 = AL::Math::Pose2D(1.0f, 0.7f, 0.5f);
  pPos2D2 = pPos2D1;
  pPos2D2 += pPos2D1;
  pPos2D1 += pPos2D1;
  EXPECT_TRUE(pPos2D2.isNear(pPos2D1));

  // operator - (a-b)
  pPos2D1 = AL::Math::Pose2D(0.5f, +0.3f, 0.1f);
  pPos2D2 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D = pPos2D2 - pPos2D1;
  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(0.0f, -0.6f, 0.0f), 0.001f));

  // operator - (-a)
  pPos2D1 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D  = AL::Math::Pose2D();
  pPos2D  = - pPos2D1;
  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(-0.5f, 0.3f, -0.1f), 0.001f));

  // operator -=
  pPos2D1 = AL::Math::Pose2D(0.6f, -0.4f, 0.2f);
  pPos2D2 = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);
  pPos2D1 -= pPos2D2;
  EXPECT_TRUE(pPos2D1.isNear(AL::Math::Pose2D(0.1f, -0.1f, 0.1f), 0.001f));

  // operator *
  pPos2D1 = AL::Math::Pose2D(1.0f, 0.0f, 0.0f);
  pPos2D2 = AL::Math::Pose2D(0.0f, 1.0f, 0.2f);
  pPos2D = pPos2D1*pPos2D2;
  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(1.0f, 1.0f, 0.2f), 0.001f));

//  // operator *
//  pPos2D1 = AL::Math::Pose2D(-0.13027f, 0.348845f, 3.14151f);
//  pPos2D2 = AL::Math::Pose2D(0.1f, 0.0f, 0.0f);
//  pPos2D = pPos2D1*pPos2D2;
//  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(-0.212624f, 0.349525f, 3.14155f), 0.001f));

  // operator *=
  pPos2D1 = AL::Math::Pose2D(1.0f, 0.0f, 0.0f);
  pPos2D2 = AL::Math::Pose2D(0.0f, 1.0f, 0.2f);
  pPos2D1 *= pPos2D2;
  EXPECT_TRUE(pPos2D1.isNear(AL::Math::Pose2D(1.0f, 1.0f, 0.2f), 0.001f));

  pPos2D1 = AL::Math::Pose2D(1.0f, 0.7f, 0.5f);
  pPos2D2 = pPos2D1;
  pPos2D2 *= pPos2D1;
  pPos2D1 *= pPos2D1;
  EXPECT_TRUE(pPos2D2.isNear(pPos2D1));
}


TEST(ALPose2DTest, isNear)
{
  AL::Math::Pose2D pPos2D = AL::Math::Pose2D(0.5f, -0.3f, 0.1f);

  EXPECT_TRUE(pPos2D.isNear(AL::Math::Pose2D(0.5f, -0.3f, 0.1f), 0.0001f));
  EXPECT_FALSE(pPos2D.isNear(AL::Math::Pose2D(0.4f, -0.3f, 0.1f), 0.0001f));
  EXPECT_FALSE(pPos2D.isNear(AL::Math::Pose2D(0.5f, -0.4f, 0.1f), 0.0001f));
  EXPECT_FALSE(pPos2D.isNear(AL::Math::Pose2D(0.5f, -0.3f, 0.2f), 0.0001f));
}

TEST(ALPose2DTest, distance)
{
  AL::Math::Pose2D pPos2D = AL::Math::Pose2D(1.0f, 2.0f, 3.0f);
  EXPECT_NEAR(pPos2D.distance(AL::Math::Pose2D(1.0f, 1.0f, 1.0f)), 1.0f, 0.0001f);
}

TEST(ALPose2DTest, distanceSquared)
{
  AL::Math::Pose2D pPos2D = AL::Math::Pose2D(1.0f, 2.0f, 3.0f);
  EXPECT_NEAR(pPos2D.distanceSquared(AL::Math::Pose2D(0.0f, 0.0f, 0.0f)), 5.0f, 0.0001f);
}

TEST(ALPose2DTest, pose2DInverse)
{
  AL::Math::Pose2D pPose2D = AL::Math::Pose2D(0.1f, -0.1f, -0.5f);
  AL::Math::Pose2D pResult = AL::Math::Pose2D(-0.135701f, 0.0398157f, 0.5f);
  AL::Math::Pose2D pInverse = pPose2D.inverse();
  EXPECT_TRUE(pInverse.isNear(pResult, 0.0001f));
  EXPECT_TRUE((pInverse*pPose2D).isNear(AL::Math::Pose2D(), 0.0001f));

  pInverse = AL::Math::Pose2D();
  pInverse = AL::Math::pose2DInverse(pPose2D);
  EXPECT_TRUE(pInverse.isNear(pResult, 0.0001f));
  EXPECT_TRUE((pInverse*pPose2D).isNear(AL::Math::Pose2D(), 0.0001f));

  pInverse = AL::Math::Pose2D();
  AL::Math::pose2DInverse(pPose2D, pInverse);
  EXPECT_TRUE(pInverse.isNear(pResult, 0.0001f));
  EXPECT_TRUE((pInverse*pPose2D).isNear(AL::Math::Pose2D(), 0.0001f));
}

TEST(ALPose2DTest, pose2dInvertInPlace)
{
  // We use pose2dInverse, previously tested
  AL::Math::Pose2D pIn = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  AL::Math::Pose2D pCopy = pIn;
  AL::Math::Pose2D pInverse = AL::Math::pose2DInverse(pIn);

  AL::Math::pose2dInvertInPlace(pIn);

  EXPECT_TRUE(pIn.isNear(pInverse, 0.0001f));
  EXPECT_TRUE((pIn*pCopy).isNear(AL::Math::Pose2D(), 0.0001f));
}

TEST(ALPose2DTest, pose2dDiff)
{
  // We use pose2dInverse, previously tested
  //inverse(pPos1)*pPos2
  AL::Math::Pose2D pPos1   = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  AL::Math::Pose2D pPos2   = pPos1;
  AL::Math::Pose2D pResult = AL::Math::pose2dDiff(pPos2, pPos1);

  EXPECT_TRUE(pResult.isNear(AL::Math::Pose2D(), 0.0001f));

  pPos1   = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  pPos2   = AL::Math::Pose2D(-1.2f, 0.1f, -10.0f*AL::Math::TO_RAD);
  pResult = AL::Math::pose2dDiff(pPos1, pPos2);

  AL::Math::Pose2D pExpected = AL::Math::pose2DInverse(pPos1)*pPos2;
  EXPECT_TRUE(pResult.isNear(pExpected, 0.0001f));
}


TEST(ALPose2DTest, diff)
{
  // We use pose2dInverse, previously tested
  //inverse(pPos1)*pPos2
  AL::Math::Pose2D pPos1   = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  AL::Math::Pose2D pPos2   = pPos1;
  AL::Math::Pose2D pResult = pPos1.diff(pPos2);

  EXPECT_TRUE(pResult.isNear(AL::Math::Pose2D(), 0.0001f));

  pPos1   = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  pPos2   = AL::Math::Pose2D(-1.2f, 0.1f, -10.0f*AL::Math::TO_RAD);
  pResult = pPos1.diff(pPos2);

  AL::Math::Pose2D pExpected = AL::Math::pose2DInverse(pPos1)*pPos2;
  EXPECT_TRUE(pResult.isNear(pExpected, 0.0001f));
}

TEST(ALPose2DTest, pinv)
{
  // We use pose2dInverse, previously tested
  //inverse(pPos1)*pPos2
  AL::Math::Pose2D pPos1   = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  AL::Math::Pose2D pPos2   = AL::Math::pinv(pPos1);
  AL::Math::Pose2D pResult = pPos2*pPos1;
  EXPECT_TRUE(pResult.isNear(AL::Math::Pose2D(), 0.0001f));

  pPos1     = AL::Math::Pose2D(0.2f, -2.1f, 35.0f*AL::Math::TO_RAD);
  pResult   = AL::Math::pinv(pPos1);
  AL::Math::Pose2D pExpected = AL::Math::pose2DInverse(pPos1);

  EXPECT_TRUE(pResult.isNear(pExpected, 0.0001f));
}

TEST(ALPose2DTest, fromPolar)
{
  const float eps = 1e-4f;
  AL::Math::Pose2D pPos1 = AL::Math::Pose2D::fromPolarCoordinates(0.2f, 0.3f);
  EXPECT_TRUE(pPos1.isNear(AL::Math::Pose2D(0.191f, 0.0591f, 0.3f), eps));

  AL::Math::Pose2D pPos2 = AL::Math::Pose2D::fromPolarCoordinates(0.0f, 0.3f);
  EXPECT_TRUE(pPos2.isNear(AL::Math::Pose2D(0.0f, 0.00f, 0.3f), eps));

  AL::Math::Pose2D pPos3 = AL::Math::Pose2D::fromPolarCoordinates(0.5f, 0.0f);
  EXPECT_TRUE(pPos3.isNear(AL::Math::Pose2D(0.5f, 0.00f, 0.0f), eps));
}

TEST(ALPose2DTest, norm)
{
  const float eps = 1e-4f;
  AL::Math::Pose2D pPos1(1.0f, 0.0f, 2.0f);
  EXPECT_NEAR(pPos1.norm(), 1.0f, eps);

  AL::Math::Pose2D pPos2(-1.0f, 0.0f, 2.0f);
  EXPECT_NEAR(pPos2.norm(), 1.0f, eps);

  AL::Math::Pose2D pPos3(0.0f, 1.5f, 2.0f);
  EXPECT_NEAR(pPos3.norm(), 1.5f, eps);

  AL::Math::Pose2D pPos4(1.5f, -1.0f, 2.0f);
  EXPECT_NEAR(pPos4.norm(), 1.8028f, eps);

  AL::Math::Pose2D pPos5(1.9f, -2.1f, 2.0f);
  AL::Math::Pose2D pPos6(-1.9f, -2.1f, 2.0f);
  AL::Math::Pose2D pPos7(-1.9f, 2.1f, 2.0f);
  EXPECT_NEAR(pPos5.norm(), pPos6.norm(), eps);
  EXPECT_NEAR(pPos7.norm(), pPos6.norm(), eps);
  EXPECT_NEAR(pPos5.norm(), pPos5.norm(), eps);
}

TEST(ALPose2DTest, normalize)
{
  const float eps = 1e-4f;
  AL::Math::Pose2D pPos0(0.0f, 0.0f, 0.0f);
  EXPECT_THROW(pPos0.normalize(), std::runtime_error);
  AL::Math::Pose2D pPos01(0.001f, 0.0f, 0.0f);
  EXPECT_NO_THROW(pPos01.normalize());

  AL::Math::Pose2D pPos1(1.0f, 0.0f, 2.0f);
  AL::Math::Pose2D n1 = pPos1.normalize();
  EXPECT_NEAR(n1.norm(), 1.0f, eps);
  EXPECT_NEAR(pPos1.getAngle(), n1.getAngle(), eps);
  EXPECT_NEAR(pPos1.theta, n1.theta, eps);

  AL::Math::Pose2D pPos2(-1.0f, 0.0f, 2.0f);
  AL::Math::Pose2D n2 = pPos2.normalize();
  EXPECT_NEAR(n2.norm(), 1.0f, eps);
  EXPECT_NEAR(pPos2.getAngle(), n2.getAngle(), eps);
  EXPECT_NEAR(pPos2.theta, n2.theta, eps);

  AL::Math::Pose2D pPos3(0.0f, 1.5f, 2.0f);
  AL::Math::Pose2D n3 = pPos3.normalize();
  EXPECT_NEAR(n3.norm(), 1.0f, eps);
  EXPECT_NEAR(pPos3.getAngle(), n3.getAngle(), eps);
  EXPECT_NEAR(pPos3.theta, n3.theta, eps);

  AL::Math::Pose2D pPos4(1.5f, -1.0f, 2.0f);
  AL::Math::Pose2D n4 = pPos4.normalize();
  EXPECT_NEAR(n4.norm(), 1.0f, eps);
  EXPECT_NEAR(pPos4.getAngle(), n4.getAngle(), eps);
  EXPECT_NEAR(pPos4.theta, n4.theta, eps);

  AL::Math::Pose2D pPos5(1.9f, -2.1f, 2.0f);
  AL::Math::Pose2D n5 = pPos5.normalize();
  EXPECT_NEAR(n5.norm(), 1.0f, eps);
  EXPECT_NEAR(pPos5.getAngle(), n5.getAngle(), eps);
  EXPECT_NEAR(pPos5.theta, n5.theta, eps);

}

TEST(ALPose2DTest, getAngle)
{
  const float eps = 1e-4f;
  AL::Math::Pose2D pos1(1.5f, 0.0f, 0.0f);
  EXPECT_NEAR(pos1.getAngle(), 0.0f, eps);

  AL::Math::Pose2D pos2(1.5f, 0.0f, 2.0f);
  EXPECT_NEAR(pos2.getAngle(), 0.0f, eps);

  AL::Math::Pose2D pos3(1.0f, -1.0f, 2.0f);
  EXPECT_NEAR(pos3.getAngle(), -AL::Math::PI_4, eps);

  AL::Math::Pose2D pos4(0.0f, 1.0f, 1.0f);
  EXPECT_NEAR(pos4.getAngle(), AL::Math::PI_2, eps);

  AL::Math::Pose2D pos5(-2.0f, 1.0f, 2.0f);
  EXPECT_NEAR(pos5.getAngle(), 2.67792f, eps);
}

TEST(ALPose2DTest, toVector)
{
  const float eps = 1e-4f;
  const AL::Math::Pose2D pos(1.0f, 2.0f, 3.0f);
  const std::vector<float> vec = pos.toVector();

  ASSERT_EQ(3u, vec.size());
  EXPECT_NEAR(pos.x, vec[0], eps);
  EXPECT_NEAR(pos.y, vec[1], eps);
  EXPECT_NEAR(pos.theta, vec[2], eps);
}

TEST(ALPose2DTest, writeToVector)
{
  const AL::Math::Pose2D pos(1.0f, 2.0f, 3.0f);

  std::vector<float> vec(6, 0.0f);
  std::vector<float>::iterator it = vec.begin();

  pos.writeToVector(it);
  pos.writeToVector(it);

  EXPECT_FLOAT_EQ(pos.x, vec[0]);
  EXPECT_FLOAT_EQ(pos.y, vec[1]);
  EXPECT_FLOAT_EQ(pos.theta, vec[2]);
  EXPECT_FLOAT_EQ(pos.x, vec[3]);
  EXPECT_FLOAT_EQ(pos.y, vec[4]);
  EXPECT_FLOAT_EQ(pos.theta, vec[5]);
}
