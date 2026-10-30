(define (problem problem-test7)
  (:domain Sokoban)
  (:objects
    box0 - box
    box1 - box
    box2 - box
    U D L R - direction
    pos_0_0 - position
    pos_6_0 - position
    pos_7_0 - position
    pos_0_1 - position
    pos_2_1 - position
    pos_3_1 - position
    pos_4_1 - position
    pos_7_1 - position
    pos_0_2 - position
    pos_2_2 - position
    pos_4_2 - position
    pos_5_2 - position
    pos_2_3 - position
    pos_3_3 - position
    pos_4_3 - position
    pos_5_3 - position
    pos_6_3 - position
    pos_1_4 - position
    pos_2_4 - position
    pos_3_4 - position
    pos_4_4 - position
    pos_5_4 - position
    pos_6_4 - position
    pos_1_5 - position
    pos_2_5 - position
    pos_3_5 - position
    pos_5_6 - position
    pos_6_6 - position
    pos_7_6 - position
  )
  (:init
    (guard-at pos_1_4)
    (box-at pos_2_3)
    (box-at pos_2_4)
    (box-at pos_4_4)
    (clear pos_0_0)
    (adjacent pos_0_0 pos_0_1 D)
    (clear pos_6_0)
    (adjacent pos_6_0 pos_7_0 R)
    (clear pos_7_0)
    (adjacent pos_7_0 pos_7_1 D)
    (adjacent pos_7_0 pos_6_0 L)
    (clear pos_0_1)
    (adjacent pos_0_1 pos_0_0 U)
    (adjacent pos_0_1 pos_0_2 D)
    (clear pos_2_1)
    (adjacent pos_2_1 pos_2_2 D)
    (adjacent pos_2_1 pos_3_1 R)
    (clear pos_3_1)
    (adjacent pos_3_1 pos_2_1 L)
    (adjacent pos_3_1 pos_4_1 R)
    (clear pos_4_1)
    (adjacent pos_4_1 pos_4_2 D)
    (adjacent pos_4_1 pos_3_1 L)
    (clear pos_7_1)
    (adjacent pos_7_1 pos_7_0 U)
    (clear pos_0_2)
    (adjacent pos_0_2 pos_0_1 U)
    (clear pos_2_2)
    (adjacent pos_2_2 pos_2_1 U)
    (adjacent pos_2_2 pos_2_3 D)
    (clear pos_4_2)
    (adjacent pos_4_2 pos_4_1 U)
    (adjacent pos_4_2 pos_4_3 D)
    (adjacent pos_4_2 pos_5_2 R)
    (clear pos_5_2)
    (adjacent pos_5_2 pos_5_3 D)
    (adjacent pos_5_2 pos_4_2 L)
    (adjacent pos_2_3 pos_2_2 U)
    (adjacent pos_2_3 pos_2_4 D)
    (adjacent pos_2_3 pos_3_3 R)
    (clear pos_3_3)
    (adjacent pos_3_3 pos_3_4 D)
    (adjacent pos_3_3 pos_2_3 L)
    (adjacent pos_3_3 pos_4_3 R)
    (clear pos_4_3)
    (adjacent pos_4_3 pos_4_2 U)
    (adjacent pos_4_3 pos_4_4 D)
    (adjacent pos_4_3 pos_3_3 L)
    (adjacent pos_4_3 pos_5_3 R)
    (clear pos_5_3)
    (adjacent pos_5_3 pos_5_2 U)
    (adjacent pos_5_3 pos_5_4 D)
    (adjacent pos_5_3 pos_4_3 L)
    (adjacent pos_5_3 pos_6_3 R)
    (clear pos_6_3)
    (adjacent pos_6_3 pos_6_4 D)
    (adjacent pos_6_3 pos_5_3 L)
    (adjacent pos_1_4 pos_1_5 D)
    (adjacent pos_1_4 pos_2_4 R)
    (adjacent pos_2_4 pos_2_3 U)
    (adjacent pos_2_4 pos_2_5 D)
    (adjacent pos_2_4 pos_1_4 L)
    (adjacent pos_2_4 pos_3_4 R)
    (clear pos_3_4)
    (adjacent pos_3_4 pos_3_3 U)
    (adjacent pos_3_4 pos_3_5 D)
    (adjacent pos_3_4 pos_2_4 L)
    (adjacent pos_3_4 pos_4_4 R)
    (adjacent pos_4_4 pos_4_3 U)
    (adjacent pos_4_4 pos_3_4 L)
    (adjacent pos_4_4 pos_5_4 R)
    (clear pos_5_4)
    (adjacent pos_5_4 pos_5_3 U)
    (adjacent pos_5_4 pos_4_4 L)
    (adjacent pos_5_4 pos_6_4 R)
    (clear pos_6_4)
    (adjacent pos_6_4 pos_6_3 U)
    (adjacent pos_6_4 pos_5_4 L)
    (clear pos_1_5)
    (adjacent pos_1_5 pos_1_4 U)
    (adjacent pos_1_5 pos_2_5 R)
    (clear pos_2_5)
    (adjacent pos_2_5 pos_2_4 U)
    (adjacent pos_2_5 pos_1_5 L)
    (adjacent pos_2_5 pos_3_5 R)
    (clear pos_3_5)
    (adjacent pos_3_5 pos_3_4 U)
    (adjacent pos_3_5 pos_2_5 L)
    (clear pos_5_6)
    (adjacent pos_5_6 pos_6_6 R)
    (clear pos_6_6)
    (adjacent pos_6_6 pos_5_6 L)
    (adjacent pos_6_6 pos_7_6 R)
    (clear pos_7_6)
    (adjacent pos_7_6 pos_6_6 L)
  )
  (:goal
    (and
      (box-at pos_5_2)
      (box-at pos_4_3)
      (box-at pos_3_4)
    )
  )
)
