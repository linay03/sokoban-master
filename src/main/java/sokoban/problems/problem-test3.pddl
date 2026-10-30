(define (problem problem-test3)
  (:domain Sokoban)
  (:objects
    box0 - box
    box1 - box
    box2 - box
    U D L R - direction
    pos_0_0 - position
    pos_5_0 - position
    pos_6_0 - position
    pos_0_1 - position
    pos_2_1 - position
    pos_3_1 - position
    pos_5_1 - position
    pos_6_1 - position
    pos_2_2 - position
    pos_3_2 - position
    pos_1_3 - position
    pos_2_3 - position
    pos_3_3 - position
    pos_4_3 - position
    pos_5_3 - position
    pos_1_4 - position
    pos_3_4 - position
    pos_4_4 - position
    pos_5_4 - position
    pos_1_5 - position
    pos_2_5 - position
    pos_3_5 - position
    pos_4_5 - position
    pos_3_6 - position
    pos_4_6 - position
    pos_6_6 - position
    pos_0_7 - position
    pos_1_7 - position
    pos_6_7 - position
  )
  (:init
    (guard-at pos_3_1)
    (box-at pos_2_2)
    (box-at pos_3_2)
    (box-at pos_2_5)
    (clear pos_0_0)
    (adjacent pos_0_0 pos_0_1 D)
    (clear pos_5_0)
    (adjacent pos_5_0 pos_5_1 D)
    (adjacent pos_5_0 pos_6_0 R)
    (clear pos_6_0)
    (adjacent pos_6_0 pos_6_1 D)
    (adjacent pos_6_0 pos_5_0 L)
    (clear pos_0_1)
    (adjacent pos_0_1 pos_0_0 U)
    (clear pos_2_1)
    (adjacent pos_2_1 pos_2_2 D)
    (adjacent pos_2_1 pos_3_1 R)
    (adjacent pos_3_1 pos_3_2 D)
    (adjacent pos_3_1 pos_2_1 L)
    (clear pos_5_1)
    (adjacent pos_5_1 pos_5_0 U)
    (adjacent pos_5_1 pos_6_1 R)
    (clear pos_6_1)
    (adjacent pos_6_1 pos_6_0 U)
    (adjacent pos_6_1 pos_5_1 L)
    (adjacent pos_2_2 pos_2_1 U)
    (adjacent pos_2_2 pos_2_3 D)
    (adjacent pos_2_2 pos_3_2 R)
    (adjacent pos_3_2 pos_3_1 U)
    (adjacent pos_3_2 pos_3_3 D)
    (adjacent pos_3_2 pos_2_2 L)
    (clear pos_1_3)
    (adjacent pos_1_3 pos_1_4 D)
    (adjacent pos_1_3 pos_2_3 R)
    (clear pos_2_3)
    (adjacent pos_2_3 pos_2_2 U)
    (adjacent pos_2_3 pos_1_3 L)
    (adjacent pos_2_3 pos_3_3 R)
    (clear pos_3_3)
    (adjacent pos_3_3 pos_3_2 U)
    (adjacent pos_3_3 pos_3_4 D)
    (adjacent pos_3_3 pos_2_3 L)
    (adjacent pos_3_3 pos_4_3 R)
    (clear pos_4_3)
    (adjacent pos_4_3 pos_4_4 D)
    (adjacent pos_4_3 pos_3_3 L)
    (adjacent pos_4_3 pos_5_3 R)
    (clear pos_5_3)
    (adjacent pos_5_3 pos_5_4 D)
    (adjacent pos_5_3 pos_4_3 L)
    (clear pos_1_4)
    (adjacent pos_1_4 pos_1_3 U)
    (adjacent pos_1_4 pos_1_5 D)
    (clear pos_3_4)
    (adjacent pos_3_4 pos_3_3 U)
    (adjacent pos_3_4 pos_3_5 D)
    (adjacent pos_3_4 pos_4_4 R)
    (clear pos_4_4)
    (adjacent pos_4_4 pos_4_3 U)
    (adjacent pos_4_4 pos_4_5 D)
    (adjacent pos_4_4 pos_3_4 L)
    (adjacent pos_4_4 pos_5_4 R)
    (clear pos_5_4)
    (adjacent pos_5_4 pos_5_3 U)
    (adjacent pos_5_4 pos_4_4 L)
    (clear pos_1_5)
    (adjacent pos_1_5 pos_1_4 U)
    (adjacent pos_1_5 pos_2_5 R)
    (adjacent pos_2_5 pos_1_5 L)
    (adjacent pos_2_5 pos_3_5 R)
    (clear pos_3_5)
    (adjacent pos_3_5 pos_3_4 U)
    (adjacent pos_3_5 pos_3_6 D)
    (adjacent pos_3_5 pos_2_5 L)
    (adjacent pos_3_5 pos_4_5 R)
    (clear pos_4_5)
    (adjacent pos_4_5 pos_4_4 U)
    (adjacent pos_4_5 pos_4_6 D)
    (adjacent pos_4_5 pos_3_5 L)
    (clear pos_3_6)
    (adjacent pos_3_6 pos_3_5 U)
    (adjacent pos_3_6 pos_4_6 R)
    (clear pos_4_6)
    (adjacent pos_4_6 pos_4_5 U)
    (adjacent pos_4_6 pos_3_6 L)
    (clear pos_6_6)
    (adjacent pos_6_6 pos_6_7 D)
    (clear pos_0_7)
    (adjacent pos_0_7 pos_1_7 R)
    (clear pos_1_7)
    (adjacent pos_1_7 pos_0_7 L)
    (clear pos_6_7)
    (adjacent pos_6_7 pos_6_6 U)
  )
  (:goal
    (and
      (box-at pos_3_2)
      (box-at pos_3_3)
      (box-at pos_3_4)
    )
  )
)
