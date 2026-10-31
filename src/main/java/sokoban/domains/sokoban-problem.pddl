(define (problem Sokoban_generated)
  (:domain Sokoban)
  (:objects
    pos_0_0 - position
    pos_1_0 - position
    pos_0_1 - position
    pos_1_1 - position
    pos_3_1 - position
    pos_4_1 - position
    pos_3_2 - position
    pos_4_2 - position
    pos_1_3 - position
    pos_2_3 - position
    pos_3_3 - position
    pos_4_3 - position
    pos_1_4 - position
    pos_3_4 - position
    pos_4_4 - position
    pos_1_5 - position
    pos_2_5 - position
    pos_3_5 - position
    pos_4_5 - position
    pos_2_6 - position
    pos_3_6 - position
    pos_4_6 - position
    pos_0_7 - position
    U D L R - direction
  )
  (:init
    (guard-at pos_3_4)
    (box-at pos_3_3)
    (box-at pos_2_5)
    (box-at pos_3_5)
    (destination pos_4_2)
    (destination pos_3_3)
    (destination pos_3_5)
    (clear pos_0_0)
    (clear pos_1_0)
    (clear pos_0_1)
    (clear pos_1_1)
    (clear pos_3_1)
    (clear pos_4_1)
    (clear pos_3_2)
    (clear pos_4_2)
    (clear pos_1_3)
    (clear pos_2_3)
    (clear pos_4_3)
    (clear pos_1_4)
    (clear pos_4_4)
    (clear pos_1_5)
    (clear pos_4_5)
    (clear pos_2_6)
    (clear pos_3_6)
    (clear pos_4_6)
    (clear pos_0_7)
    (adjacent pos_0_0 pos_0_1 D)
    (adjacent pos_0_0 pos_1_0 R)
    (adjacent pos_1_0 pos_1_1 D)
    (adjacent pos_1_0 pos_0_0 L)
    (adjacent pos_0_1 pos_0_0 U)
    (adjacent pos_0_1 pos_1_1 R)
    (adjacent pos_1_1 pos_1_0 U)
    (adjacent pos_1_1 pos_0_1 L)
    (adjacent pos_3_1 pos_3_2 D)
    (adjacent pos_3_1 pos_4_1 R)
    (adjacent pos_4_1 pos_4_2 D)
    (adjacent pos_4_1 pos_3_1 L)
    (adjacent pos_3_2 pos_3_1 U)
    (adjacent pos_3_2 pos_3_3 D)
    (adjacent pos_3_2 pos_4_2 R)
    (adjacent pos_4_2 pos_4_1 U)
    (adjacent pos_4_2 pos_4_3 D)
    (adjacent pos_4_2 pos_3_2 L)
    (adjacent pos_1_3 pos_1_4 D)
    (adjacent pos_1_3 pos_2_3 R)
    (adjacent pos_2_3 pos_1_3 L)
    (adjacent pos_2_3 pos_3_3 R)
    (adjacent pos_3_3 pos_3_2 U)
    (adjacent pos_3_3 pos_3_4 D)
    (adjacent pos_3_3 pos_2_3 L)
    (adjacent pos_3_3 pos_4_3 R)
    (adjacent pos_4_3 pos_4_2 U)
    (adjacent pos_4_3 pos_4_4 D)
    (adjacent pos_4_3 pos_3_3 L)
    (adjacent pos_1_4 pos_1_3 U)
    (adjacent pos_1_4 pos_1_5 D)
    (adjacent pos_3_4 pos_3_3 U)
    (adjacent pos_3_4 pos_3_5 D)
    (adjacent pos_3_4 pos_4_4 R)
    (adjacent pos_4_4 pos_4_3 U)
    (adjacent pos_4_4 pos_4_5 D)
    (adjacent pos_4_4 pos_3_4 L)
    (adjacent pos_1_5 pos_1_4 U)
    (adjacent pos_1_5 pos_2_5 R)
    (adjacent pos_2_5 pos_2_6 D)
    (adjacent pos_2_5 pos_1_5 L)
    (adjacent pos_2_5 pos_3_5 R)
    (adjacent pos_3_5 pos_3_4 U)
    (adjacent pos_3_5 pos_3_6 D)
    (adjacent pos_3_5 pos_2_5 L)
    (adjacent pos_3_5 pos_4_5 R)
    (adjacent pos_4_5 pos_4_4 U)
    (adjacent pos_4_5 pos_4_6 D)
    (adjacent pos_4_5 pos_3_5 L)
    (adjacent pos_2_6 pos_2_5 U)
    (adjacent pos_2_6 pos_3_6 R)
    (adjacent pos_3_6 pos_3_5 U)
    (adjacent pos_3_6 pos_2_6 L)
    (adjacent pos_3_6 pos_4_6 R)
    (adjacent pos_4_6 pos_4_5 U)
    (adjacent pos_4_6 pos_3_6 L)
  )
  (:goal
    (and
      (box-at pos_4_2)
      (box-at pos_3_3)
      (box-at pos_3_5)
    )
  )
)
