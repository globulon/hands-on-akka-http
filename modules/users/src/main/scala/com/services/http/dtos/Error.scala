package com.services.http.dtos

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.types.all.NonEmptyString

sealed trait Error
case object NoError extends Error

final case class FoundError(
  title: NonEmptyString,
  description: List[String Refined NonEmpty]
) extends Error
