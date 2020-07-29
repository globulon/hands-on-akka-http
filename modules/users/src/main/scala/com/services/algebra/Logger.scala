package com.services.algebra

trait Logger[F[_]] {
  def info: String => F[Unit]
}
