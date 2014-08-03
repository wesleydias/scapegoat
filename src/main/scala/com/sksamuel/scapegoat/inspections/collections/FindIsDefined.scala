package com.sksamuel.scapegoat.inspections.collections

import com.sksamuel.scapegoat._

/** @author Stephen Samuel */
class FindIsDefined extends Inspection {

  def inspector(context: InspectionContext): Inspector = new Inspector(context) {
    override def traverser = new context.Traverser {

      import context.global._

      override def inspect(tree: Tree): Unit = {
        tree match {
          case Select(Apply(Select(_, TermName("find")), _), TermName("isDefined")) =>
            context.warn("use exists() not find().isDefined()", tree.pos, Levels.Info,
              ".find(x => Bool).isDefined can be replaced with exists(x => Bool): " + tree.toString().take(500))
          case _ => super.traverse(tree)
        }
      }
    }
  }
}