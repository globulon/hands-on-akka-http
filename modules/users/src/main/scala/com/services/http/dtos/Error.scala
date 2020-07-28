package com.services.http.dtos

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

sealed trait Error
case object NoError extends Error

final case class FoundError(
  title: String Refined NonEmpty,
  description: List[String Refined NonEmpty]
) extends Error
